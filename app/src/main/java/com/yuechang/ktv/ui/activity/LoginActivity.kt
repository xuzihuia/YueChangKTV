package com.yuechang.ktv.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yuechang.ktv.R
import com.yuechang.ktv.util.PreferenceManager
import kotlinx.coroutines.launch

/**
 * 登录页
 * 支持手机号密码登录、短信验证码登录
 */
class LoginActivity : AppCompatActivity() {
    
    private lateinit var etPhone: EditText
    private lateinit var etPassword: EditText
    private lateinit var etSmsCode: EditText
    private lateinit var btnPasswordLogin: TextView
    private lateinit var btnSmsLogin: TextView
    private lateinit var btnGetSmsCode: TextView
    private lateinit var preferenceManager: PreferenceManager
    
    private var isPasswordMode = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        preferenceManager = PreferenceManager(this)
        
        // 已登录则直接跳转
        if (preferenceManager.isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
        
        initViews()
        setupListeners()
    }
    
    private fun initViews() {
        etPhone = findViewById(R.id.et_phone)
        etPassword = findViewById(R.id.et_password)
        etSmsCode = findViewById(R.id.et_sms_code)
        btnPasswordLogin = findViewById(R.id.btn_password_login)
        btnSmsLogin = findViewById(R.id.btn_sms_login)
        btnGetSmsCode = findViewById(R.id.btn_get_sms_code)
    }
    
    private fun setupListeners() {
        // 密码登录
        btnPasswordLogin.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            val password = etPassword.text.toString().trim()
            
            if (validatePhone(phone) && validatePassword(password)) {
                performLogin(phone, password, null)
            }
        }
        
        // 短信登录
        btnSmsLogin.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            val code = etSmsCode.text.toString().trim()
            
            if (validatePhone(phone) && validateSmsCode(code)) {
                performLogin(phone, null, code)
            }
        }
        
        // 获取验证码
        btnGetSmsCode.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            if (validatePhone(phone)) {
                requestSmsCode(phone)
            }
        }
        
        // 切换登录方式
        findViewById<TextView>(R.id.tv_switch_mode).setOnClickListener {
            isPasswordMode = !isPasswordMode
            updateLoginMode()
        }
    }
    
    private fun updateLoginMode() {
        if (isPasswordMode) {
            etPassword.visibility = android.view.View.VISIBLE
            etSmsCode.visibility = android.view.View.GONE
            btnGetSmsCode.visibility = android.view.View.GONE
        } else {
            etPassword.visibility = android.view.View.GONE
            etSmsCode.visibility = android.view.View.VISIBLE
            btnGetSmsCode.visibility = android.view.View.VISIBLE
        }
    }
    
    private fun validatePhone(phone: String): Boolean {
        if (phone.isEmpty()) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show()
            return false
        }
        if (phone.length != 11) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    
    private fun validatePassword(password: String): Boolean {
        if (password.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(this, "密码至少6位", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    
    private fun validateSmsCode(code: String): Boolean {
        if (code.isEmpty()) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show()
            return false
        }
        if (code.length != 6) {
            Toast.makeText(this, "验证码为6位数字", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    
    private fun requestSmsCode(phone: String) {
        lifecycleScope.launch {
            // TODO: 调用API发送验证码
            Toast.makeText(this@LoginActivity, "验证码已发送", Toast.LENGTH_SHORT).show()
            
            // 开始倒计时
            var countdown = 60
            btnGetSmsCode.isEnabled = false
            btnGetSmsCode.text = "${countdown}s"
            
            while (countdown > 0) {
                kotlinx.coroutines.delay(1000)
                countdown--
                btnGetSmsCode.text = "${countdown}s"
            }
            
            btnGetSmsCode.isEnabled = true
            btnGetSmsCode.text = "获取验证码"
        }
    }
    
    private fun performLogin(phone: String, password: String?, smsCode: String?) {
        lifecycleScope.launch {
            // TODO: 调用API登录
            // 模拟登录成功
            preferenceManager.isLoggedIn = true
            preferenceManager.userId = "user_$phone"
            
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }
    
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}