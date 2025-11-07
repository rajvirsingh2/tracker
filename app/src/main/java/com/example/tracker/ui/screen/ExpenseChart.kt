package com.example.tracker.ui.screen

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tracker.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

@Composable
fun ExpenseChart(modifier: Modifier= Modifier, data:Map<String, Float>) {
    AndroidView(
        modifier= modifier,
        factory = {
            PieChart(it).apply {
                description.isEnabled=false
                isDrawHoleEnabled=true
                setHoleColor(R.color.white)
                setUsePercentValues(true)
                setEntryLabelColor(Color.BLACK)
                setEntryLabelTextSize(12f)
                legend.isEnabled=false
            }
        },
        update = {
            val entries=data.map { PieEntry(it.value,it.key) }
            val set= PieDataSet(entries,"Expenses").apply {
                colors=ColorTemplate.MATERIAL_COLORS.toList()
                valueTextColor=Color.BLACK
                valueTextSize=14f
            }
            it.data= PieData(set)
            it.invalidate()
        }
    )
}