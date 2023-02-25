package com.example.poetrytour.ui.mine

import android.R.attr
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.ui.User
import kotlinx.android.synthetic.main.activity_mine_material.*
import java.net.URLDecoder


class MineMaterialActivity : AppCompatActivity(), View.OnClickListener {
    
    val viewModel by lazy { ViewModelProvider(this).get(MineViewModel::class.java) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_material)

        mine_change_tx.setOnClickListener(this)
        xingbie_forward.setOnClickListener(this)
        nicheng_forward.setOnClickListener(this)
//        tel_forward.setOnClickListener(this)
        update_mine_material.setOnClickListener(this)

        mine_material_back.setOnClickListener {
            finish()
        }

        Glide.with(ContextTool.getContext())
            .load(User.avatar)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(mine_change_tx)

        mine_material_name.setText(User.user_name)
        mine_material_tel.setText(User.login_user!!.user_tel)
        mine_material_sex.setText(User.login_user!!.sex)
        mine_material_intro.setText(User.login_user!!.intro)
        
        viewModel.updateLiveData.observe(this){
            Toast.makeText(ContextTool.getContext(),"更新成功",Toast.LENGTH_SHORT).show()
            User.user_name=it.user_name
            User.avatar=it.avatar
            User.login_user=it
        }
        viewModel.imgUrlLiveData.observe(this){
            if (it.isSuccess) {
                val img=URLDecoder.decode(it.data,"UTF-8")
//                Glide.with(this).load(img)
//                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
//                    .into(mine_change_tx)
                Toast.makeText(this,"更改成功",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.mine_change_tx->{
                openGallery(1)
            }
            R.id.xingbie_forward->{
                
                val items = arrayOf("男", "女")
                var sex=items[0]
                val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
                alertBuilder.setTitle("设置性别")
                alertBuilder.setSingleChoiceItems(items, 0) { dialogInterface, i ->
                    sex=items[i]
                }
                alertBuilder.setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        mine_material_sex.setText(sex)
                       
                    })
                alertBuilder.setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialogInterface, i ->  })
                val alertDialog = alertBuilder.create()
                alertDialog.show()
            }
            R.id.nicheng_forward->{
                var inputServer = EditText(this)
                inputServer.setText(mine_material_name.text.toString())
                val builder = AlertDialog.Builder(this)
                builder.setTitle("设置昵称")
                    .setView(inputServer)
                    .setNegativeButton(
                        "取消"
                    ) { dialog, which -> dialog.dismiss() }
    
                builder.setPositiveButton(
                    "确定"
                ) { dialog, which ->
                    if(inputServer.text.toString().isNullOrEmpty()){
                        Toast.makeText(ContextTool.getContext(),"不能为空",Toast.LENGTH_SHORT).show()
                    }else{
                        mine_material_name.setText(inputServer.text.toString())
                        dialog.dismiss()
                    }
                }
                val alertDialog = builder.create()
                alertDialog.show()
            }
            R.id.update_mine_material->{
                var user=User.login_user
                user!!.user_name=mine_material_name.text.toString()
                user!!.sex=mine_material_sex.text.toString()
                user!!.intro=mine_material_intro.text.toString()
                viewModel.setUpdateUserLiveData(user)
            }
        }
    }
    private fun openGallery(type: Int) {
        val gallery = Intent(Intent.ACTION_PICK)
        gallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(gallery, type)
    }
    
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (data.data != null) {
                Glide.with(this).load(data.data)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(mine_change_tx)
        
        
                val selectedImage: Uri = data.data!!
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor? =
                    contentResolver.query(selectedImage, filePathColumn, null, null, null)
                if (cursor != null) {
                    cursor.moveToFirst()
                    val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                    val picturePath: String = cursor.getString(columnIndex)
                    println(picturePath)
                    viewModel.setImgPathLiveData(picturePath)
                }
                
                
                
            }
        }
    }
    
}