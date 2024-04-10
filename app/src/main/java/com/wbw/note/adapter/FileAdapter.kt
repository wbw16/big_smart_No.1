package com.wbw.note.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.wbw.note.EditActivity
import com.wbw.note.R
import com.wbw.note.adapter.FileAdapter.FileViewHolder

class FileAdapter(private val fileList: List<String>,private val context: Context) :
    RecyclerView.Adapter<FileViewHolder>() {
    class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textFileName: TextView = itemView.findViewById(R.id.text_file_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileName = fileList[position]
        holder.textFileName.text = fileName
        holder.itemView.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra("fileName", fileName) // 将文件名添加到 Intent 中
            intent.putExtra("mode", 1)
            /**
             * mode 为 1 时，表示修改模式
             * mode 为 0 时，表示新建模式
             */
            /**
             * mode 为 1 时，表示修改模式
             * mode 为 0 时，表示新建模式
             */
            startActivity(context, intent, null)
        }
        //        holder.bind(fileName, listener);
//        holder.mRootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, EditActivity.class);
//                intent.putExtra("fileName", fileName); // 将文件名添加到 Intent 中
//                intent.putExtra("mode",1);
//                /**
//                 * mode 为 1 时，表示修改模式
//                 * mode 为 0 时，表示新建模式
//                 */
//
//                context.startActivity(intent);
////                startActivity(intent);
//            }
//        });
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    

}
