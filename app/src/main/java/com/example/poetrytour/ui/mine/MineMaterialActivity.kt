package com.example.poetrytour.ui.mine

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.ui.User
import kotlinx.android.synthetic.main.activity_mine_material.*
import java.io.File
import java.util.regex.Matcher
import java.util.regex.Pattern


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
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.mine_change_tx->{
                val builder = AlertDialog.Builder(this)
                builder.setPositiveButton("相机"){ _, _ ->
                    run {
                        ActivityCompat.requestPermissions(
                            this@MineMaterialActivity,
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ),
                            1
                        )

                        DongTaiShare()
                        getPicFromCamera()
                    }
                }
                builder.setNegativeButton("相册"){ _, _ ->getPicFromAlbm()}
                val alert = builder.create()
                alert.show()
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
//                user!!.user_tel=mine_material_tel.text.toString()
                user!!.sex=mine_material_sex.text.toString()
                user!!.intro=mine_material_intro.text.toString()
                viewModel.setUpdateUserLiveData(user)
            }
//            R.id.tel_forward->{
//                var inputServer = EditText(this)
//                inputServer.setText(mine_material_tel.text.toString())
//                val builder = AlertDialog.Builder(this)
//                builder.setTitle("设置电话")
//                    .setView(inputServer)
//                    .setNegativeButton(
//                        "取消"
//                    ) { dialog, which -> dialog.dismiss() }
//
//                builder.setPositiveButton(
//                    "确定"
//                ) { dialog, which ->
//                    if(inputServer.text.toString().isNullOrEmpty()){
//                        Toast.makeText(ContextTool.getContext(),"不能为空",Toast.LENGTH_SHORT).show()
//                    }else{
//                        val tel=inputServer.text.toString()
//                        val p: Pattern = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$") //正则
//                        val m: Matcher = p.matcher(tel)
//                        if(m.matches()) {
//                            mine_material_tel.setText(tel)
//                            dialog.dismiss()
//                        }else{
//                            Toast.makeText(ContextTool.getContext(),"输入正确的号码",Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//                val alertDialog = builder.create()
//                alertDialog.show()
//            }

        }
    }

    private fun getPicFromAlbm() {
        startActivityForResult(Intent(Intent.ACTION_PICK).setType("image/*"),2)
    }

    private fun getPicFromCamera() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE),1)
    }

    private fun DongTaiShare() {
        if(Build.VERSION.SDK_INT>=23){
            val mPermissionList = arrayOf<String>(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS,
                Manifest.permission.CAMERA
            )
            ActivityCompat.requestPermissions(this, mPermissionList, 123)
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            1->{if(resultCode== RESULT_OK) run {
                var bitmap: Bitmap = data?.getParcelableExtra<Bitmap>("data")!!
                bitmap = selfImage(bitmap,54,54)
                mine_change_tx.setImageBitmap(bitmap)
            }
            }
            2->{if(resultCode== RESULT_OK)run{
                val uri: Uri? = data?.data
                uri?.let { cropPhoto(it) }
            }}
            3->{val bundle: Bundle? = data?.extras
                if(bundle!=null){
                    var bitmap: Bitmap = bundle.getParcelable<Bitmap>("data")!!
                    bitmap = selfImage(bitmap,54,54)
                    println(bitmap)
                    mine_change_tx.setImageBitmap(bitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
    //对图片进行裁剪
    private fun cropPhoto(uri: Uri) {
        var file: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        file = File(file, "temp.jpg")
        val picUri = FileProvider.getUriForFile(this, "console.live.camera.fileprovider", file)
        val resultUri = Uri.fromFile(file) //此resultUri代表裁剪后保存的位置，可见使用了同样的file，因此如果裁剪成功会覆盖原来相机拍摄的图片。
        val intent = Intent("com.android.camera.action.CROP")
        intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
        intent.setDataAndType(picUri, "image/*")
        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputX", 54)
        intent.putExtra("outputY", 54)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, resultUri);
        intent.putExtra( "outputFormat", Bitmap.CompressFormat.PNG.toString() )
        intent.putExtra("return-data", true)
        startActivityForResult(intent, 3)
    }
    //修改图片尺寸，使其适应于头像原本大小
    private fun selfImage(bm: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
        val srcWidth = bm.width
        val srcHeight = bm.height
        val widthScale = targetWidth * 1.0f / srcWidth
        val heightScale = targetHeight * 1.0f / srcHeight
        val matrix = Matrix()
        matrix.postScale(widthScale, heightScale, 0F, 0F)
        // 如需要可自行设置 Bitmap.Config.RGB_8888 等等
        val bmpRet = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.RGB_565)
        val canvas = Canvas(bmpRet)
        val paint = Paint()
        canvas.drawBitmap(bm, matrix, paint)
        return bmpRet
    }
}