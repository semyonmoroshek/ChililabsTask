package lv.semyonmoroshek.chililabstask.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import lv.semyonmoroshek.chililabstask.databinding.ActivityMainBinding
import lv.semyonmoroshek.chililabstask.utils.hide
import lv.semyonmoroshek.chililabstask.utils.show

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mAdapter: GifListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        initView()
        initListener()
        initObserver()

        binding.edtAddress.addTextChangedListener { query ->
            if (!query.isNullOrEmpty()) {
                binding.btnClear.show()
                viewModel.searchGif(query.toString())
            } else {
                binding.btnClear.hide()
            }
        }
    }

    private fun initView() {
        val recyclerView = binding.rvGifList
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        mAdapter = GifListAdapter()
        recyclerView.adapter = mAdapter
    }

    private fun initListener() {
        binding.btnClear.setOnClickListener {
            binding.edtAddress.setText("")
            mAdapter.setUpdatedData(listOf())
        }
    }

    private fun initObserver() {
        viewModel.searchGifResp.observe(this) { resp ->
            resp?.data?.let {gifList ->
                mAdapter.setUpdatedData(gifList)
            }
        }
    }
}

