package ru.spacestar.chem.ui.calculator.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.spacestar.chem.R
import ru.spacestar.chem.ui.calculator.business.CalculatorSideEffect
import ru.spacestar.chem.ui.calculator.business.CalculatorViewModel
import ru.spacestar.chem.ui.theme.ChemTheme

@Composable
fun Calculator(viewModel: CalculatorViewModel) {
    val state by viewModel.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    viewModel.collectSideEffect {
        when (it) {
            CalculatorSideEffect.RequestFocus -> focusRequester.requestFocus()
            CalculatorSideEffect.HideKeyboard -> focusManager.clearFocus()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        OutlinedTextField(
            value = state.input,
            label = { Text(stringResource(id = R.string.calc_input_hint)) },
            onValueChange = { viewModel.input(it) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
        Text(
            text = state.result,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .weight(1f)
        )
        Button(
            onClick = { viewModel.search() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.calc_result_button))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ChemTheme {
        Calculator(viewModel())
    }
}