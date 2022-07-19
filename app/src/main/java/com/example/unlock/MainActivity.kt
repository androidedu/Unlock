package com.example.unlock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var mPassword:String ? = null //保存原始密码
    var tempPassword:String? = null  //保存设置密码时的第一次密码
    lateinit var resultTextView:TextView //显示提示信息

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lockView = findViewById<PicUnlockView>(R.id.lockView)
        resultTextView = findViewById<TextView>(R.id.textView)

        readData()//读取密码
        lockView.callBack = { password ->
            if (mPassword == null) {
                //需要设置密码
                lockView.clear()
                tempPassword = password
                resultTextView.text = "请确认密码图案"
            } else {
                //解锁密码
                if (mPassword == null) {
                    if (tempPassword.equals(password)) {
                        saveData(password)
                        resultTextView.text = "设置密码成功"
                    } else {
                        resultTextView.text = "设置密码失败"
                    }
                } else {
                    if (mPassword.equals(password)) {
                        resultTextView.text = "解锁密码成功"
                    } else {
                        resultTextView.text = "密码错误,请重新绘制密码图案"
                    }
                }
            }
        }
    }

    private fun readData(){
        lifecycleScope.launch{
            val preferences = dataStore.data.first()

            val key = stringPreferencesKey("password")
            mPassword = preferences[key]
            if (mPassword == null){
                resultTextView.text = "请设置密码图案"
            }else{
                resultTextView.text = "请绘制解锁图案"
            }
        }
    }


    //向dataStore写入数据 文件封装类
    private fun saveData(password:String){
        lifecycleScope.launch {
            dataStore.edit {
                val key = stringPreferencesKey("password")
                it[key] = password
            }
        }
    }


}


















