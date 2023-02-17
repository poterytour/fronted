package com.example.poetrytour.ui.mine

import android.Manifest
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.poetrytour.R
import com.makeramen.roundedimageview.RoundedImageView
import java.io.File


class MineMaterialActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mine_change_tx:RoundedImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_material)
        mine_change_tx = findViewById(R.id.mine_change_tx)
        mine_change_tx.setOnClickListener(this)
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