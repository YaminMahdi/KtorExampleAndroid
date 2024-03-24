package com.ktor.exp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ktor.exp.domain.RequestState
import com.ktor.exp.ui.theme.KtorExpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val userList by viewModel.userList.collectAsStateWithLifecycle()
            val user by viewModel.userInfo.collectAsStateWithLifecycle()
            KtorExpTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ){
                        Login(onLoginClick = { pn, pass->
                            viewModel.login(pn,pass){
                                when (it) {
                                    is RequestState.Error -> Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                                    is RequestState.Success -> {
                                        viewModel.getUserList{er->
                                            if (er is RequestState.Error)
                                                Toast.makeText(this@MainActivity, er.error, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        })
                        AnimatedVisibility(visible = user.name.isNotEmpty()) {
                            Column {
                                Text("USER INFO", Modifier.fillMaxWidth().padding(start = 10.dp, top = 10.dp))
                                ListItem(
                                    headlineContent = { Text("Name: " + user.name) },
                                    supportingContent = { Text("Phone: " +user.phone) }
                                )
                                HorizontalDivider()
                            }
                        }
                        AnimatedVisibility(visible = userList.isNotEmpty()){
                            Column {
                                Text("USER LIST", Modifier.fillMaxWidth().padding(start = 10.dp, top = 10.dp))
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    items(userList){
                                        ListItem(
                                            headlineContent = { Text(it.name) },
                                            supportingContent = { Text(it.phone) }
                                        )
                                    }
                                }
                            }
                        }
                    }


                }
            }
        }
    }
}

@Composable
fun Login(
    onLoginClick : (String,String) -> Unit,
) {
    var pn by rememberSaveable { mutableStateOf("11111111117") }
    var pass by rememberSaveable { mutableStateOf("1234") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(value = pn, onValueChange = {pn=it}, label = { Text("Phone")})
        OutlinedTextField(value = pass, onValueChange = {pass=it}, label = { Text("Password")})
        Spacer(modifier = Modifier.size(20.dp))
        Button(onClick = { onLoginClick.invoke(pn,pass) }) { Text("LOGIN") }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KtorExpTheme {
        Login({ a, b->})
    }
}