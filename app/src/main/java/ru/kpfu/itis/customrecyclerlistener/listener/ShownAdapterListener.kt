package ru.kpfu.itis.customrecyclerlistener.listener

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class ShownAdapterListener(
    private val shownPercentage: Int,
) : RecyclerView.OnScrollListener() {

    private var lastToast: Toast?  = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        when (val layoutManager = recyclerView.layoutManager) {
            is GridLayoutManager -> {
                if (dy != 0){
                    handleGridLayout(layoutManager,recyclerView, dy,true)
                }
                if (dx != 0){
                    handleGridLayout(layoutManager,recyclerView,dx,false)
                }
            }

            is LinearLayoutManager -> {
                if (dy != 0){
                    handleLinearLayout(layoutManager,recyclerView, dy,true)
                }
                if (dx != 0){
                    handleLinearLayout(layoutManager,recyclerView,dx,false)
                }
            }

            is StaggeredGridLayoutManager ->{
                if (dy != 0){
                    handleStaggeredGridLayout(layoutManager,recyclerView, dy,true)
                }
                if (dx != 0){
                    handleStaggeredGridLayout(layoutManager,recyclerView,dx,false)
                }
            }
        }
    }

    private fun handleStaggeredGridLayout(
        layoutManager: StaggeredGridLayoutManager,
        recyclerView: RecyclerView,
        d: Int,
        isVertical: Boolean
    ) {

        val lastVisibleItems: List<Int>
        val isFirst: Boolean

        if (d > 0){
            isFirst = false
            lastVisibleItems = layoutManager.findLastVisibleItemPositions(null).toList()
        }else{
            isFirst = true
            lastVisibleItems = layoutManager.findFirstVisibleItemPositions(null).toList()
        }

        val itemsPercents = mutableListOf<Int>()
        // считаем проценты для элементов
        for (i in lastVisibleItems){
            if (isVertical){
                itemsPercents.add(
                    calculateVisiblePercentVertical(layoutManager,i,recyclerView,isFirst)
                )
            }else{
                itemsPercents.add(
                    calculateVisiblePercentHorizontal(layoutManager,i,recyclerView,isFirst)
                )
            }
        }
        var message = ""
        for (percent in itemsPercents.withIndex()){
            if (percent.value in shownPercentage .. 100){
                message += "Элемент №${lastVisibleItems[percent.index] + 1} виден на ${percent.value}% \n"
            }
        }

        if (message.isNotBlank()){
            lastToast?.cancel()
            lastToast = Toast.makeText(recyclerView.context,message,Toast.LENGTH_SHORT)
            lastToast!!.show()
        }

    }

    private fun handleGridLayout(
        layoutManager: GridLayoutManager,
        recyclerView: RecyclerView,
        d: Int,
        isVertical: Boolean
    ) {
        val itemPosition: Int
        val isFirst: Boolean

        if (d > 0){
            isFirst = false
            itemPosition = layoutManager.findLastVisibleItemPosition()
        }else{
            isFirst = true
            itemPosition = layoutManager.findFirstVisibleItemPosition()
        }


        val visiblePercent = if (isVertical){
            calculateVisiblePercentVertical(layoutManager,itemPosition,recyclerView,isFirst)
        }else{
            calculateVisiblePercentHorizontal(layoutManager,itemPosition,recyclerView,isFirst)
        }

        if (visiblePercent in (shownPercentage + 1)..100){
            lastToast?.cancel()
            var message = ""
            for (i in 1 .. layoutManager.spanCount){
                message += "Элемент №${itemPosition+i} виден на $visiblePercent% \n"
            }
            lastToast = Toast.makeText(recyclerView.context,message,Toast.LENGTH_SHORT)
            lastToast!!.show()
        }
    }


    private fun handleLinearLayout(
        layoutManager: LinearLayoutManager,
        recyclerView: RecyclerView,
        d: Int,
        isVertical: Boolean
    ) {

        val itemPosition: Int
        val isFirst: Boolean

        if (d > 0){
            isFirst = false
            itemPosition = layoutManager.findLastVisibleItemPosition()
        }else{
            isFirst = true
            itemPosition = layoutManager.findFirstVisibleItemPosition()
        }


        val visiblePercent = if (isVertical){
            calculateVisiblePercentVertical(layoutManager,itemPosition,recyclerView,isFirst)
        }else{
            calculateVisiblePercentHorizontal(layoutManager,itemPosition,recyclerView,isFirst)
        }

        if (visiblePercent in (shownPercentage + 1)..100){
            lastToast?.cancel()
            lastToast = Toast.makeText(recyclerView.context,"Элемент №${itemPosition+1} виден на $visiblePercent%",Toast.LENGTH_SHORT)
            lastToast!!.show()
        }
    }

    private fun calculateVisiblePercentHorizontal(
        layoutManager: LayoutManager,
        itemPosition: Int,
        recyclerView: RecyclerView,
        first: Boolean
    ): Int {
        val item = layoutManager.findViewByPosition(itemPosition) ?: return 0
        val visibleWidth = if (!first) recyclerView.width - item.left else item.right

        return (visibleWidth.toFloat()/item.width.toFloat() * 100).toInt()
    }

    private fun calculateVisiblePercentVertical(
        layoutManager: LayoutManager,
        position: Int,
        recyclerView: RecyclerView,
        first: Boolean
    ): Int {
        val item = layoutManager.findViewByPosition(position) ?: return 0
        // rv.height - высота rv для экрана, item.top - координата вершины элемента отн экрана
        val visibleHeight = if (!first) recyclerView.height - item.top else item.bottom

        return (visibleHeight.toFloat() / item.height.toFloat() * 100).toInt()
    }
}