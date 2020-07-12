package com.studentsbazaar.studentsbazaarapp

import android.app.ProgressDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.crowdfire.cfalertdialog.CFAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.studentsbazaar.studentsbazaarapp.controller.Controller
import com.studentsbazaar.studentsbazaarapp.controller.SendSMS
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil
import kotlinx.android.synthetic.main.verify_phone_number.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class VerifyPhoneNumber : AppCompatActivity() {

    private lateinit var phno : String
    private lateinit var otp : String
    var ctime: CountDownTimer? = null
    private lateinit var pd : ProgressDialog
    var CURRENT_TIME = 0
    var LIMIT_TIME1 = 9
    var LIMIT_TIME2 = 9


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verify_phone_number)

        vphone_phno.setText("Please check your mobile number is ${intent.getStringExtra("ph")}")
        phno = intent.getStringExtra("ph")

        setSupportActionBar(vphone_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val c = Calendar.getInstance()
        CURRENT_TIME = c[Calendar.HOUR]
        if (LIMIT_TIME1 >= CURRENT_TIME && LIMIT_TIME2 <= CURRENT_TIME) {
            val builder = CFAlertDialog.Builder(this@VerifyPhoneNumber)
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            builder.setCornerRadius(20f)
            builder.setContentImageDrawable(R.drawable.timing_closed_icon)
            builder.setTitle("Sorry !")
            builder.setMessage("OTP timing 9AM to 9PM only . So please try again after some time")
            builder.addButton("OK", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
            ) { dialog, which ->
                dialog.dismiss()
                finish()
            }
            builder.show()
        }

    }

    override fun onResume() {
        super.onResume()

        vphone_change.setOnClickListener(View.OnClickListener {
            vphone_change_box.visibility = View.VISIBLE
            vphone_change.visibility = View.GONE
            vphone_continue.visibility = View.GONE
        })

        vphone_continue.setOnClickListener(View.OnClickListener {
            vphone_otp_box.visibility = View.VISIBLE
            vphone_change.visibility = View.GONE
            vphone_continue.visibility = View.GONE
            otp = getRandomNumberString().toString()
            val hand = Handler()
            hand.postDelayed(Runnable {
                SendSMS(this ,phno , otp)
                vphone_otp_resendbtn.visibility = View.GONE
                Hour(90)
            },2000)
        })

        vphone_change_phno_btn.setOnClickListener(View.OnClickListener {
            phno = vphone_change_phno.editText?.text.toString()
            vphone_phno.setText("Please check your mobile number is $phno")
            vphone_change_box.visibility = View.GONE
            vphone_change.visibility = View.VISIBLE
            vphone_continue.visibility = View.VISIBLE
        })

        vphone_otp_verifybtn.setOnClickListener(View.OnClickListener {
            ctime?.cancel()
            pd = ProgressDialog.show(this@VerifyPhoneNumber , "Updating" ,"Please wait...")
            if (otp.equals(vphone_otp_code.text.toString())){
                val call : Call<String>
                call = ApiUtil.getServiceClass().updatephsts(ApiUtil.UPDATE_PHSTS+"?ph=${phno}&uid=${Controller.getUID()}")
                call.enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        pd.dismiss()
                        Log.d("Verify phone error" , t.message)
                    }
                    override fun onResponse(call: Call<String>, response: Response<String>) =
                            if(response.isSuccessful){
                                Log.d("verify phone sts " , response.body())
                                if(response.body().equals("0")) {
                                    pd.dismiss()
                                    Controller(this@VerifyPhoneNumber).addmobsts("1")
                                    Controller(this@VerifyPhoneNumber).addphno(phno)
                                    finish()
                                }else{
                                    pd.dismiss()
                                    Snackbar.make(vphone_screen , "Update Error Please try again" , Snackbar.LENGTH_SHORT).show()
                                    vphone_change.visibility = View.VISIBLE
                                    vphone_continue.visibility = View.VISIBLE
                                    vphone_otp_box.visibility = View.GONE
                                    vphone_change.visibility = View.GONE
                                }
                            }else{
                                pd.dismiss()
                                Snackbar.make(vphone_screen , "Update Error Please try again" , Snackbar.LENGTH_SHORT).show()
                                vphone_change.visibility = View.VISIBLE
                                vphone_continue.visibility = View.VISIBLE
                                vphone_otp_box.visibility = View.GONE
                                vphone_change.visibility = View.GONE
                            }
                })
            }else{
                pd.dismiss()
                Snackbar.make(vphone_screen , "Incorrect OTP Please check" , Snackbar.LENGTH_SHORT).show()
            }
        })

        vphone_otp_resendbtn.setOnClickListener(View.OnClickListener {
            otp = getRandomNumberString().toString()
            val hand = Handler()
            hand.postDelayed(Runnable {
                SendSMS(this ,phno , otp)
            },2000)
        })

        vphone_toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })

    }

    fun Hour(s: Int) {
        ctime = object : CountDownTimer((s * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                vphone_otp_time.setText("waiting for otp  :  " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                vphone_otp_resendbtn.visibility = View.VISIBLE
                vphone_otp_time.setText("Re send OTP")
            }
        }.start()
    }

    fun getRandomNumberString(): String? {
        val rnd = Random()
        val number = rnd.nextInt(999999)
        return String.format("%06d", number)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
