package com.example.tracker.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tracker.data.viewmodel.DashboardViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onAddExpense:()->Unit,
    onLogout:()->Unit,
    viewModel: DashboardViewModel
)
{
    val expenses by viewModel.expenses.collectAsState()
    val categoryTotal=expenses.groupBy { it.category }.mapValues { it.value.sumOf { it.amount }.toFloat() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    IconButton(onClick = {
                        viewModel.logout()
                        onLogout()
                    }) {
                        Icon(Icons.Default.ExitToApp,"LogOut")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExpense) {
                Icon(Icons.Default.Add,"Add Expense")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            item {
                if(categoryTotal.isNotEmpty()){
                    ExpenseChart(
                        Modifier.fillMaxWidth().height(250.dp),
                        categoryTotal
                    )
                }else{
                    Box(Modifier.fillMaxWidth().height(250.dp), contentAlignment = Alignment.Center){
                        Text("No Expense data available")
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text("Recent Expenses", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(8.dp))
            }
            if(expenses.isEmpty()){
                item {
                    Box(Modifier.fillMaxWidth().padding(top=20.dp), contentAlignment = Alignment.Center) {
                        Text("Click '+' icon to get started.")
                    }
                }
            }else{
                items(expenses){
                    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        ListItem(
                            headlineContent = {Text(it.title)},
                            supportingContent = {Text(it.category)},
                            trailingContent = {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(String.format("₹%.2f", it.amount))
                                    Text(
                                        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                                            Date(it.date)
                                        ),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}