package com.example.task2

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_add_habit.*
import kotlinx.android.synthetic.main.fragment_habits.*
import kotlinx.android.synthetic.main.fragment_about.*
import java.util.*

// TO DO
// поправить при переходе из нав дравера в 'о приложении' и потом на домашнюю страницу (ВЫЛЕТАЕТ)
// фильтр работает не очень правильно, поправить
// плавающая кнопка должна плавать
// ресайклер вью должен быть до боттом шита
// вылезает клавиатура
// HabitModel переписать иф на when
// TODO
class About : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
}