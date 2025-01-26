package com.appsbysha.saywhat.ui


import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appsbysha.saywhat.R
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.LineType
import com.appsbysha.saywhat.viewmodels.SayingEditViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


/**
 * Created by sharone on 29/11/2024.
 */


object Catalog {

    @Composable
    fun Saying(
        paddingValues: PaddingValues,
        listOfLines: List<Line>,
        mainChild: Child,
        editMode: Boolean,
        viewModel: SayingEditViewModel? = null,
    ) {
        var isExpanded by remember { mutableStateOf(false) }

        LazyColumn(modifier = Modifier
            .background(Color.Cyan)
            .clickable { isExpanded = !isExpanded }
            .heightIn(max = if (isExpanded || editMode) 1000.dp else 100.dp)
            .padding(paddingValues)

        ) {
            itemsIndexed(listOfLines) { index, item ->
                Sentence(
                    line = item,
                    index = index,
                    imgResource = mainChild.profilePic?.toUri(),
                    otherPersonName = item.otherPerson,
                    editMode = editMode,
                    viewModel
                )
            }
        }
    }

    @Composable
    fun Sentence(
        line: Line,
        index: Int,
        imgResource: Uri? = null,
        otherPersonName: String? = null,
        editMode: Boolean,
        viewModel: SayingEditViewModel? = null,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),

            horizontalArrangement = when (line.lineType) {
                LineType.OTHER_PERSON -> {
                    Arrangement.End
                }

                LineType.NOTE -> {
                    Arrangement.Center
                }

                else -> {//LineType.MAIN_CHILD
                    Arrangement.Start
                }
            }
        ) {
            if (editMode) {
                Image(painter = painterResource(R.drawable.remove_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(26.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel?.onRemoveLine(line) })
            }

            if (line.lineType == LineType.MAIN_CHILD) {
                imgResource?.let {
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .align(Alignment.Bottom)
                    ) {

                        ProfilePicFromUri(24.dp, it)

                    }
                }

            }
            SpeechBubble(
                line = line,
                index = index,
                otherPersonName = otherPersonName,
                editMode = editMode,
                viewModel = viewModel
            )

        }
    }

    @Composable
    fun SpeechBubble(
        line: Line,
        index: Int,
        otherPersonName: String? = null,
        editMode: Boolean,
        viewModel: SayingEditViewModel? = null,
    ) {

        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (line.lineType == LineType.MAIN_CHILD) 0f else 48f,
                        bottomEnd = if (line.lineType == LineType.OTHER_PERSON) 0f else 48f
                    )
                )
                .background(Color.Green)
                .padding(12.dp)
        ) {
            Column {
                if (line.lineType == LineType.OTHER_PERSON) {
                    if (editMode) {
                        var nameText by remember { mutableStateOf(otherPersonName ?: "") }
                        // Update textFieldValue when line.text changes
                        LaunchedEffect(otherPersonName ?: "") {
                            nameText = otherPersonName ?: ""
                        }
                        TextField(value = nameText, onValueChange = {
                            nameText = it
                            viewModel?.updateOtherPersonName(index, it)
                        }, label = { Text("Enter name here") })
                    } else {
                        if (otherPersonName != null) {
                            Text(text = otherPersonName, fontSize = 12.sp)
                        }
                    }
                }
                if (editMode) {
                    // Directly use line.text for the TextField value
                    var speechText by remember { mutableStateOf(line.text) }

                    // Update textFieldValue when line.text changes
                    LaunchedEffect(line.text) {
                        speechText = line.text
                    }

                    Log.i(
                        "SayingEditViewModel",
                        "editMode print sentence textFieldValue $speechText"
                    )

                    TextField(
                        value = speechText, onValueChange = {
                            speechText = it
                            viewModel?.updateLine(index, it)

                            Log.i(
                                "SayingEditViewModel",
                                "editMode print sentence line.text ${line.text}"
                            )

                        },
                        label = { Text("Enter text here") }
                    )
                } else {
                    Text(text = line.text, fontSize = 16.sp)
                }
            }
        }
    }

    @Composable
    fun ChildCardView(child: Child, modifier: Modifier, onRemoveClick: () -> Unit) {
        Card(
            shape = RoundedCornerShape(8.dp), modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                child.profilePic?.toUri()?.let {
                    ProfilePicFromUri(24.dp, it)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = child.name, style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = child.dob.toString(), style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "numOfSayings ${child.sayings.size}",
                        style = MaterialTheme.typography.body2
                    )
                    Text(text = "Remove",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.clickable { onRemoveClick() }
                    )
                }
            }
        }
    }


    @Composable
    fun LineToolBar(child: Child, viewModel: SayingEditViewModel?) {

        Row {
            child.profilePic?.toUri()?.let {
                ProfilePicFromUri(
                    64.dp,
                    it,
                ) { viewModel?.onAddLineClick(lineType = LineType.MAIN_CHILD) }
            } ?: run {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile_icon),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .matchParentSize()
                            .size(64.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { viewModel?.onAddLineClick(lineType = LineType.MAIN_CHILD) })

                    Text(
                        text = child.name,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            }
            Image(painter = painterResource(R.drawable.speech_bubble_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f)
                    .graphicsLayer(scaleX = -1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { viewModel?.onAddLineClick(lineType = LineType.OTHER_PERSON) }

            )
            Image(painter = painterResource(R.drawable.note_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { viewModel?.onAddLineClick(lineType = LineType.NOTE) }

            )
            var ageText by remember { mutableStateOf("") }

            TextField(value = ageText,
                { newValue ->
                    if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*\$"))) {
                        ageText = newValue
                        viewModel?.onAgeUpdated(newValue.toFloatOrNull() ?: 0f)
                    }
                },
                modifier = Modifier.width(60.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Age") })
            Image(painter = painterResource(R.drawable.save_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { viewModel?.onSaveClick() }

            )
            Image(painter = painterResource(R.drawable.remove_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { viewModel?.onRemoveAllClick() }

            )
        }
    }


    @Composable
    fun InputDialog(
        onDismiss: () -> Unit = {},
        onSubmit: (String, Long, Uri?) -> Unit,
    ) {
        var name by remember { mutableStateOf("") }
        var dateOfBirth by remember { mutableStateOf<Long?>(null) }
        var imageUri by remember { mutableStateOf<Uri?>(null) }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Input Details") },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DatePicker(
                        selectedDateMillis = dateOfBirth,
                        onDateSelected = { dateOfBirth = it })
                    Spacer(modifier = Modifier.height(8.dp))
                    ImagePicker(onImageSelected = { imageUri = it })
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSubmit(name, dateOfBirth ?: 0, imageUri)
                        onDismiss()
                    }
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }

    @Composable
    fun ConfirmRemoveDialog(
        name: String,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit,
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "Confirmation")
            },
            text = {
                Text(text = "Are you sure you want to remove $name?")
            },
            confirmButton = {
                Button(
                    onClick = onConfirm
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    @Composable
    fun DatePicker(selectedDateMillis: Long?, onDateSelected: (Long) -> Unit) {
        val context = LocalContext.current
        val calendar =
            Calendar.getInstance()
        selectedDateMillis?.let { calendar.timeInMillis = it }
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(
                    year,
                    month,
                    dayOfMonth
                )
                onDateSelected(calendar.timeInMillis)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        Button(onClick =
        { datePickerDialog.show() })
        { Text(text = "Select Date of Birth") }
        selectedDateMillis?.let {
            val selectedDate = Date(it)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            Text(
                text =
                "Selected Date: ${dateFormat.format(selectedDate)}"
            )
        }
    }


    @Composable
    fun ImagePicker(onImageSelected: (Uri) -> Unit) {
        val context = LocalContext.current
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        var fileName by remember { mutableStateOf<String?>(null) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri = uri
            uri?.let {
                fileName = getFileName(context, it)
            }

        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { launcher.launch("image/*") }) {
                Text(text = "Upload Image")
            }
            fileName?.let {
                Text(text = "Selected file: $it")
            }
            imageUri?.let {
                onImageSelected(it)
            }
        }
    }


    private fun getFileName(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                it.getString(nameIndex)
            } else null
        }
    }


    @Composable
    fun ProfilePicFromUri(size: Dp, uri: Uri?, onImageClick: (() -> Unit?)? = null) {
        uri?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(it)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.profile_icon),
                contentDescription = "profile_pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(size)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        if (onImageClick != null) {
                            onImageClick()
                        }
                    },
                colorFilter = ColorFilter.tint(Color.Blue)
            )
        }
    }

}