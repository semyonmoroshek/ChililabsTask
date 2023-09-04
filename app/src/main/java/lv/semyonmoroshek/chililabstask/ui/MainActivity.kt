package lv.semyonmoroshek.chililabstask.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import lv.semyonmoroshek.chililabstask.databinding.ActivityMainBinding


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()
    private val mAdapter: GifListAdapter by lazy {
        GifListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListener()
    }

    private fun initView() {
        val recyclerView = binding.rvGifList
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = mAdapter
    }

    private fun initListener() {
        binding.btnClear.setOnClickListener {
            binding.edtAddress.setText("")
        }

        binding.edtAddress.addTextChangedListener { query ->
            viewModel.searchGif(query.toString()).observe(this) {
                mAdapter.submitData(lifecycle, it)
            }
            binding.btnClear.isVisible = !query.isNullOrEmpty()
        }
    }
}

