package com.example.todoapp.view

import com.example.todoapp.view.EditTodoFragmentArgs
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.viewmodel.DetailTodoViewModel
import com.example.todoapp.viewmodel.ListTodoViewModel


class EditTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo
            , container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        val txtJudulTodo = view.findViewById<TextView>(R.id.txtJudul)
        txtJudulTodo.text = "Edit Todo"
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.text = "Save"

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)

        btnAdd.setOnClickListener{
            val radioGroupPritority = view.findViewById<RadioGroup>(R.id.radioGroupPriority)
            val radio = view.findViewById<RadioButton>(radioGroupPritority.checkedRadioButtonId)
            val txtTitle = view.findViewById<EditText>(R.id.txtTitle)
            val txtNotes = view.findViewById<EditText>(R.id.txtNotes)

            viewModel.update(txtTitle.text.toString(),txtNotes.text.toString(), radio.tag.toString().toInt(),uuid)
            Toast.makeText(context,"Todo updates",Toast.LENGTH_SHORT).show()
        }
        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer{
            val txtTitle = view?.findViewById<EditText>(R.id.txtTitle)
            val txtNotes = view?.findViewById<EditText>(R.id.txtNotes)

            txtTitle?.setText(it.title)
            txtNotes?.setText(it.notes)

            val radioHigh = view?.findViewById<RadioButton>(R.id.radioHigh)
            val radioMedium = view?.findViewById<RadioButton>(R.id.radioMedium)
            val radioLow = view?.findViewById<RadioButton>(R.id.radioLow)
            when(it.priority){
                1 -> radioLow?.isChecked = true
                2 ->radioMedium?.isChecked = true
                3 ->radioHigh?.isChecked = true
            }
        })
    }
}