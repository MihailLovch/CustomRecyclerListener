package ru.kpfu.itis.customrecyclerlistener

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

            recycler.layoutManager = StaggeredGridLayoutManager(
                3,
                StaggeredGridLayoutManager.VERTICAL
            )
        }

    }
}