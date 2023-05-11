package com.example.mrcantt2.Citas

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.mrcantt2.R
import com.example.mrcantt2.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CitasAdapter (

    var context : Context,
    var listaCitas : ArrayList<Cita>,
    private val cancelarCitaListener: (position: Int) -> Unit // Paso 1: Agregar parámetro


): RecyclerView.Adapter<CitasAdapter.CitaViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.cardview_cita, parent, false)
        return CitaViewHolder(vista)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = listaCitas[position]


        // Paso 2: Configurar listener para botón de cancelar
        holder.ivCancelarCita.setOnClickListener {
            val cancelDialog = Dialog(context)
            cancelDialog.setContentView(R.layout.cancel_dialog)
            cancelDialog.setCancelable(false)

            cancelDialog.findViewById<Button>(R.id.btnNo_Cancelar).setOnClickListener {
                cancelDialog.dismiss()
            }

            val motivoEditText = cancelDialog.findViewById<EditText>(R.id.etMotivoCancelacion)
            cancelDialog.findViewById<Button>(R.id.btnSi_Cancelar).setOnClickListener {
                val motivo = motivoEditText.text.toString().trim()
                if (motivo.isEmpty()) {
                    motivoEditText.error = "Por favor ingresa el motivo de la cancelación"
                } else {
                    // Aquí puedes realizar cualquier otra operación que necesites antes de cerrar el cuadro de diálogo
                    eliminarCita(position)
                    cancelDialog.dismiss()
                }
            }
            cancelDialog.show()
        }


        // Holder para la parte del TextView FechaCita -- Sostiene el TV del cardview Citas Proximas
        holder.tvTipoCita.text = cita.tipo_cita

        //Toma la hora de la listaCitas de la posicion hora_cita de la base de datos para convertitlo unicamente a hh:mm -- Horas y minutos
        val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        // Obtener la hora de la cita de tu objeto de datos
        val horaCita = listaCitas[position].hora_cita
        // Parsear la hora de la cita al objeto Date
        val hora = inputFormat.parse(horaCita)
        // Dar formato a la fecha en el formato deseado
        val horaFormateada = outputFormat.format(hora)

        // Holder para la parte del TextView FechaCita -- Sostiene el TV del cardview Citas Proximas
        // Asignar la hora formateada al TextView correspondiente
        holder.tvHoraCita.text = horaFormateada


        //Toma la fecha de la listaCitas de la posicion fecha_cita de la base de datos para convertitlo unicamente a dd//mm/yyyy
        val fechaCita = listaCitas[position].fecha_cita // Obtener la fecha de la cita desde el objeto Cita en la posición dada
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Crear un objeto SimpleDateFormat con el formato deseado
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(fechaCita) // Convertir la fecha de cita en un objeto Date
        val fechaFormateada = sdf.format(date) // Formatear la fecha a la cadena deseada

        // Holder para la parte del TextView FechaCita -- Sostiene el TV del cardview Citas Proximas
        // Establecer la fecha formateada en el TextView correspondiente
        holder.tvFechaCita.text = fechaFormateada

    }

    override fun getItemCount(): Int {
        return listaCitas.size
    }

    inner class CitaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvTipoCita = itemView.findViewById(R.id.tipoCitaTextView) as TextView
        val tvFechaCita = itemView.findViewById(R.id.fechaCitaTextView) as TextView
        val tvHoraCita = itemView.findViewById(R.id.horaCitaTextView) as TextView
        val ivCancelarCita = itemView.findViewById(R.id.ivCancelarCita) as ImageView
    }

    fun eliminarCita(position: Int) {
        val cita = listaCitas[position]
        val idCita = cita.id_cita // Obtener el ID de la cita que se está eliminando

        // Llamar al endpoint de cancelarCita para eliminar la cita de la base de datos
        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.webServ.cancelarCita(idCita)

            if (response.isSuccessful) {
                // Si la llamada HTTP es exitosa, eliminar la cita de la lista y notificar al adaptador
                listaCitas.removeAt(position)
                withContext(Dispatchers.Main) {
                    notifyItemRemoved(position)
                }
            } else {
                // Si la llamada HTTP no es exitosa, mostrar un mensaje de error al usuario
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error al cancelar la cita", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }






}


