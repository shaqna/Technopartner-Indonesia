package com.maou.myapplicationtest.presentation.qrcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.maou.myapplicationtest.R
import com.maou.myapplicationtest.databinding.ActivityQrcodeBinding

class QRCodeActivity : AppCompatActivity() {

    private val binding: ActivityQrcodeBinding by lazy {
        ActivityQrcodeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val qrcode = intent.getStringExtra("qr_code")

        Glide.with(this).load(qrcode).into(binding.ivQrCode)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}