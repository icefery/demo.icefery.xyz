package xyz.icefery.demo.view.baidumap

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import xyz.icefery.demo.R
import xyz.icefery.demo.databinding.FragmentBaiduMapBinding


class BaiduMapFragment : Fragment() {
    private lateinit var binding: FragmentBaiduMapBinding
    private lateinit var client: LocationClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBaiduMapBinding.inflate(inflater, container, false)

        val baiduMap = binding.baiduMapView.map

        // 指南针
        baiduMap.uiSettings.isCompassEnabled = true

        // 普通视图 | 卫星视图 | 交通图 | 热力图
        baiduMap.mapType = BaiduMap.MAP_TYPE_NORMAL
        binding.mapTypeRadioGroup.setOnCheckedChangeListener({ group, checkedId ->
            if (checkedId == R.id.map_type_normal_radio_button) {
                baiduMap.mapType = BaiduMap.MAP_TYPE_NORMAL
            } else if (checkedId == R.id.map_type_satellite_radio_button) {
                baiduMap.mapType = BaiduMap.MAP_TYPE_SATELLITE
            }
        })
        binding.trafficMapCheckbox.setOnCheckedChangeListener({ buttonView, isChecked -> baiduMap.isTrafficEnabled = isChecked })
        binding.heatMapCheckbox.setOnCheckedChangeListener({ buttonView, isChecked -> baiduMap.isBaiduHeatMapEnabled = isChecked })

        // 标记点
        baiduMap.setOnMapClickListener(object : OnMapClickListener {
            private var a: LatLng? = null
            private var b: LatLng? = null

            override fun onMapClick(latLng: LatLng) {
                when {
                    a == null -> {
                        a = latLng
                        val bundleA = Bundle()
                        bundleA.putString("name", "a")
                        val markerA = MarkerOptions().extraInfo(bundleA).position(a).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marka))
                        // A 点
                        baiduMap.addOverlay(markerA)
                        binding.markerAEditText.setText(String.format("(%.4f, %.4f)", a!!.longitude, a!!.latitude))
                    }
                    b == null -> {
                        b = latLng
                        val bundleB = Bundle()
                        bundleB.putString("name", "b")
                        val markerB = MarkerOptions().extraInfo(bundleB).position(b).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markb))
                        // B 点
                        baiduMap.addOverlay(markerB)
                        binding.markerBEditText.setText(String.format("(%.4f, %.4f)", b!!.longitude, b!!.latitude))
                        // AB
                        val markerLine = PolylineOptions().points(listOf(a, b)).color(-0x55010000).width(10)
                        baiduMap.addOverlay(markerLine)
                        // 距离
                        val distance = DistanceUtil.getDistance(a, b)
                        binding.markerDistanceEditText.setText(String.format("%.4f", distance))
                    }
                    else -> {
                        // 清空
                        a = null
                        b = null
                        baiduMap.clear()
                        binding.markerAEditText.setText("")
                        binding.markerBEditText.setText("")
                        binding.markerDistanceEditText.setText("")
                    }
                }
            }

            override fun onMapPoiClick(mapPoi: MapPoi) {}
        })

        // 罗盘定位
        val configuration = MyLocationConfiguration(
            MyLocationConfiguration.LocationMode.COMPASS,
            false,
            BitmapDescriptorFactory.fromResource(0)
        )
        baiduMap.setMyLocationConfiguration(configuration)

        client = LocationClient(requireContext().applicationContext)
        val option = LocationClientOption()
        option.isOpenGps = true
        option.setCoorType("bd09ll")
        option.setScanSpan(1000)
        option.isLocationNotify = true
        client.locOption = option

        // 位置变化监听
        client.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(location: BDLocation) {
                baiduMap.setMyLocationData(
                    MyLocationData
                        .Builder()
                        .accuracy(location.radius)
                        .direction(location.direction)
                        .longitude(location.longitude)
                        .latitude(location.latitude)
                        .build()
                )
            }
        })

        // 开始定位
        client.start()

        // 开启定位图层
        baiduMap.isMyLocationEnabled = true

        return binding.root
    }


    override fun onDestroyView() {
        client.stop()
        binding.baiduMapView.map.isMyLocationEnabled = false
        binding.baiduMapView.onDestroy()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        binding.baiduMapView.onResume()
    }

    override fun onPause() {
        binding.baiduMapView.onPause()
        super.onPause()
    }
}