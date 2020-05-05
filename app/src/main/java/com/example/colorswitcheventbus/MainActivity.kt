package com.example.colorswitcheventbus

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {

    private val TAG : String? = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v(TAG, "En onCreate():" + Thread.currentThread().id);

        EventBus.getDefault().register(this)
    }

    fun redOnClick(v: View?) {
        Log.v(TAG, "En redOnClick():" + Thread.currentThread().id)
        layoutMain.setBackgroundColor(Color.RED)
    }

    fun greenOnClick(v: View?) {
        Log.v(TAG, "En greenOnClick():" + Thread.currentThread().id)
        layoutMain.setBackgroundColor(Color.GREEN)
    }

    fun blueOnClick(v: View?) {
        Log.v(TAG, "En blueOnClick():" + Thread.currentThread().id)
        layoutMain.setBackgroundColor(Color.BLUE)
    }

    fun longCalculation(v: View?) {
        Thread(Runnable {
            Log.v(
                TAG, "En longCalculation():" +
                        Thread.currentThread().id
            )
            try {
                Thread.sleep(4000)
            } catch (e: InterruptedException) {
            }
            var messageEvent=MessageEvent("Resultado")
            EventBus.getDefault().post(messageEvent)
        }).start()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEventReceived(event:MessageEvent){
        resultField.text.append("${event.message} ")
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
