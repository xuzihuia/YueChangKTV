package com.yuechang.ktv.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yuechang.ktv.R

class LoginActivity : AppCompatActivity() {
    
    private lateinit var etPhone: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSmsLogin: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        initViews()
        setupListeners()
    }
    
    private fun initViews() {
        etPhone = findViewById(R.id.et_phone)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnSmsLogin = findViewById(R.id.btn_sms_login)
    }
    
    private fun setupListeners() {
        btnLogin.setOnClickListener {
            val phone = etPhone.text.toString()
            val password = etPassword.text.toString()
            
            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入手机号和密码", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // TODO: 实际登录逻辑
            loginSuccess()
        }
        
        btnSmsLogin.setOnClickListener {
            // TODO: 短信登录
            Toast.makeText(this, "短信登录功能开发中", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun loginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}