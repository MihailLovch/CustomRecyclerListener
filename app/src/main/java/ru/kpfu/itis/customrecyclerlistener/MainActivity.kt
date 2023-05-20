package ru.kpfu.itis.customrecyclerlistener

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kpfu.itis.customrecyclerlistener.databinding.ActivityMainBinding
import ru.kpfu.itis.customrecyclerlistener.listener.ShownAdapterListener
import ru.kpfu.itis.customrecyclerlistener.rv.Adapter

class MainActivity : AppCompatActivity() {

    private var _viewBinding: ActivityMainBinding? = null
    private val viewBinding get() = _viewBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        with(viewBinding){
            recycler.adapter = Adapter(40)
            recycler.addOnScrollListener(ShownAdapterListener(60))

//            recycler.layoutManager = StaggeredGridLayoutManager(
//                3,
//                StaggeredGridLayoutManager.VERTICAL
//            )
        }

    }
}