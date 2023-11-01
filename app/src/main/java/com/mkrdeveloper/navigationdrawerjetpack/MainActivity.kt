package com.mkrdeveloper.navigationdrawerjetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mkrdeveloper.navigationdrawerjetpack.ui.theme.NavigationDrawerJetpackTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDrawerJetpackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavDrawer()

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer() {

    val drawerItem = listOf(
        DrawerItems(Icons.Default.Face, "Profile", 0, false),
        DrawerItems(Icons.Filled.Email, "Inbox", 32, true),
        DrawerItems(Icons.Filled.Favorite, "Favorite", 32, true),
        DrawerItems(Icons.Filled.Settings, "Setting", 0, false)
    )
    val drawerItem2 = listOf(
        DrawerItems(Icons.Default.Share, "Share", 0, false),
        DrawerItems(Icons.Filled.Star, "Rate", 0, false)
    )
    var selectedItem by remember {
        mutableStateOf(drawerItem[0])
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {

            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xffffc107)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        Modifier.wrapContentSize(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.shrek2),
                            contentDescription = "profile pic",
                            modifier = Modifier
                                .size(130.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = "Mr Shrek",
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Divider(
                        Modifier.align(Alignment.BottomCenter), thickness = 1.dp,
                        Color.DarkGray
                    )

                }
                drawerItem.forEach{
                    NavigationDrawerItem(label = { Text(text = it.text) }
                        , selected = it == selectedItem
                        , onClick = {
                                selectedItem = it

                            scope.launch {
                                drawerState.close()
                            }

                                    },
                        modifier = Modifier.padding(horizontal = 16.dp)
                        , icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        }
                        , badge = {
                            if (it.hasBadge){
                                BadgedBox(badge = {
                                    Badge {
                                        Text(text = it.badgeCount.toString(), fontSize = 17.sp)
                                    }
                                }) {

                                }
                            }
                        }
                    )
                }
                Divider( thickness = 1.dp,
                    color = Color.DarkGray
                )
                drawerItem2.forEach{
                    NavigationDrawerItem(label = { Text(text = it.text) }
                        , selected = it == selectedItem
                        , onClick = {
                            selectedItem = it

                            scope.launch {
                                drawerState.close()
                            }

                        },
                        modifier = Modifier.padding(horizontal = 16.dp)
                        , icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        }
                    )
                }
            }
        }
    }, drawerState = drawerState) {

        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "drawer menu") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu Icon")
                    }

                }
            )
        }) { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                contentAlignment = Alignment.Center){
                Button(onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }) {
                    Text(text = "Open drawer")
                }
            }
        }


    }
}
data class DrawerItems(

    val icon : ImageVector,
    val text : String,
    val badgeCount : Int,
    val hasBadge : Boolean
)