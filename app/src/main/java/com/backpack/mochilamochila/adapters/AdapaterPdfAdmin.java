package com.backpack.mochilamochila.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.backpack.mochilamochila.MyApplication;
import com.backpack.mochilamochila.PdfDetailActivity;
import com.backpack.mochilamochila.databinding.RowPdfBinding;
import com.backpack.mochilamochila.models.ModelPdf;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class AdapaterPdfAdmin extends RecyclerView.Adapter<AdapaterPdfAdmin.HolderPdfAdmin>{

    private Context context;
    private ArrayList<ModelPdf> pdfArrayList;

    private RowPdfBinding binding;

    private static final String TAG = "PDF_ADAPTER_TAG";

    public AdapaterPdfAdmin(Context context, ArrayList<ModelPdf> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
    }

    @Override
    public HolderPdfAdmin onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        binding = RowPdfBinding.inflate(LayoutInflater.from(context), parent, false);

        return new HolderPdfAdmin(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NotNull AdapaterPdfAdmin.HolderPdfAdmin holder, int position) {
        ModelPdf model = pdfArrayList.get(position);
        String categoryId = model.getCategoryId();
        String pdfId = model.getId();
        String title = model.getTitle();
        String description = model.getDescription();
        String pdfUrl = model.getUrl();
        long timestamp = model.getTimestamp();

        String formatedDate = MyApplication.formatTimestamp(timestamp);

        holder.titleTv.setText(title);
        holder.descriptionTv.setText(description);
        holder.dateTv.setText(formatedDate);

        MyApplication.loadCategory(
                ""+categoryId,
                holder.categoryTv
        );
        MyApplication.loadPdfFromUrl(
                ""+pdfUrl,
                ""+title,
                holder.pdfView,
                holder.progressBar
        );
        MyApplication.loadPdfSize(
                ""+pdfUrl,
                ""+title,
                holder.sizeTv
        );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PdfDetailActivity.class);
                intent.putExtra("bookId", pdfId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfArrayList.size();
    }

    class HolderPdfAdmin extends RecyclerView.ViewHolder{

        PDFView pdfView;
        ProgressBar progressBar;
        TextView titleTv, descriptionTv, categoryTv, sizeTv, dateTv;

        public HolderPdfAdmin(@NotNull View itemView) {
            super(itemView);

            pdfView = binding.pdfView;
            progressBar = binding.progressBar;
            titleTv = binding.titleTv;
            descriptionTv = binding.descriptionTv;
            categoryTv = binding.categoryTv;
            sizeTv = binding.sizeTv;
            dateTv = binding.dateTv;
        }
    }
}
