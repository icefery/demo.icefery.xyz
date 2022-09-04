package xyz.icefery.demo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import xyz.icefery.demo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request permission
        val permissions = listOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
        ).filter({ p -> ContextCompat.checkSelfPermission(applicationContext, p) != PackageManager.PERMISSION_GRANTED })

        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 0)
        }

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        super.setContentView(binding.root)

        // Toolbar
        super.setSupportActionBar(binding.toolbar)

        val appBarConfiguration = AppBarConfiguration.Builder().build()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                val notAllowedPermissionList = permissions
                    .indices
                    .filter({ i -> grantResults[i] != PackageManager.PERMISSION_GRANTED })
                    .map({ i -> permissions[i] })
                if (notAllowedPermissionList.isNotEmpty()) {
                    AlertDialog
                        .Builder(this)
                        .setTitle("警告")
                        .setMessage("没有 ${notAllowedPermissionList} 权限, 请授予权限")
                        .setNegativeButton("好的", { dialog, which -> android.os.Process.killProcess(android.os.Process.myPid()) })
                        .create()
                }
            }
            else -> {

            }
        }
    }
}