package id.personalia.employe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.personalia.employe.Activity.OcClaim;
import id.personalia.employe.Model.Claim;
import id.personalia.employe.R;

/**
 * Created by Dian Yanzen on 9/14/2017.
 */

public class ClaimAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_TYPE_EMPTY = 2;

    private boolean loading = true;

    private List<Claim> Claims;
    private ItemClickListener ItemClickListener;
    private Context context;
    public ClaimAdapter(Context current) {
        this.context = current;
        Claims = new ArrayList<Claim>();
    }

    private void add(Claim item) {
        Claims.add(item);
        notifyItemInserted(Claims.size());
    }

    public void addAll(List<Claim> Claims) {
        for (Claim Claim : Claims) {
            add(Claim);
        }
    }

    public void remove(Claim item) {
        int position = Claims.indexOf(item);
        if (position > -1) {
            Claims.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        int size = this.Claims.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.Claims.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public Claim getItem(int position){
        return Claims.get(position);
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionFooter (0)) {
            return VIEW_TYPE_EMPTY;
        }
        else if (isPositionFooter(position)) {
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }

    private boolean isPositionFooter (int position) {
        return position == Claims.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.claim_list_layout, parent, false);
            return new ClaimViewHolder(view, ItemClickListener);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_item, parent, false);
            return new LoadingViewHolder(view);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
            return new emptyViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ClaimViewHolder) {
            ClaimViewHolder ClaimViewHolder = (ClaimViewHolder) holder;

            final Claim Claim = Claims.get(position);
            ClaimViewHolder.cliv_img.setImageResource(R.drawable.def_claim);

            switch (Claim.getCLSTATUS()){
                case "Menunggu":
                    ClaimViewHolder.cliv_status.setImageResource(R.drawable.ic_clock);
                    ClaimViewHolder.cliv_status.setColorFilter(context.getResources().getColor(R.color.arkaOrange));
                    break;
                case "Disetujui":
                    ClaimViewHolder.cliv_status.setImageResource(R.drawable.ic_done);
                    ClaimViewHolder.cliv_status.setColorFilter(context.getResources().getColor(R.color.arkaGreen));
                    break;
                case "Ditolak":
                    ClaimViewHolder.cliv_status.setImageResource(R.drawable.ic_clear);
                    ClaimViewHolder.cliv_status.setColorFilter(context.getResources().getColor(R.color.arkaRed));
                    break;
            }

            ClaimViewHolder.cliv_status.setPadding(10,10,10,10);
            ClaimViewHolder.cltv_tanggal.setText(Claim.getCLTTANGGAL());
            ClaimViewHolder.cltv_diajukan.setText(Claim.getCLNMPENGAJU());
            ClaimViewHolder.cltv_deskripsi.setText(Claim.getCLDESKRIKSI());
            ClaimViewHolder.cltv_deskrp.setText(Claim.getCLDESCRIPTION());
            ClaimViewHolder.cltv_jumlah.setText(Claim.getCLJUMLAH());
            ClaimViewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    if (isLongClick) {
                        //Toast.makeText(context, "Cukup Tekan Sekali Untuk Menampilkan Rincian Data", Toast.LENGTH_SHORT).show();
                    } else {

                        Intent intent = new Intent(activity, OcClaim.class);
                        intent.putExtra("CLNMPENGAJU", Claims.get(position).getCLNMPENGAJU()); //you can name the keys whatever you like
                        intent.putExtra("CLTTANGGAL", Claims.get(position).getCLTTANGGAL()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                        intent.putExtra("CLDESKRIKSI", Claims.get(position).getCLDESKRIKSI());
                        intent.putExtra("CLDESCRIPTION", Claims.get(position).getCLDESCRIPTION());
                        intent.putExtra("CLJUMLAH", Claims.get(position).getCLJUMLAH());
                        switch (Claims.get(position).getCLSTATUS()){
                            case "Menunggu":
                                intent.putExtra("CLSTATUS", "Menunggu");
                                break;
                            case "Disetujui":
                                intent.putExtra("CLSTATUS", "Disetujui");
                                break;
                            case "Ditolak":
                                intent.putExtra("CLSTATUS", "Ditolak");
                                break;
                        }
                        activity.startActivity(intent);
                    }
                }

            });


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        } else if (holder instanceof emptyViewHolder) {
            emptyViewHolder EmptyViewHolder = (emptyViewHolder) holder;
            EmptyViewHolder.empty_text.setText("Tidak Ada Data Klaim\nYang Dapat Ditampilkan");
        }

    }

    public void setLoading(boolean loading){
        this.loading = loading;
    }

    @Override
    public int getItemCount() {
        return Claims == null ? 0 : Claims.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
    static class emptyViewHolder extends RecyclerView.ViewHolder {
        TextView empty_text;
        public emptyViewHolder(View itemView) {
            super(itemView);
            empty_text = (TextView) itemView.findViewById(R.id.empty_text);
        }
    }

    static class ClaimViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener clickListener;
        ImageView cliv_status,cliv_img;
        TextView cltv_tanggal, cltv_diajukan, cltv_deskripsi,cltv_jumlah,cltv_deskrp;


        public ClaimViewHolder(View itemView, ItemClickListener ItemClickListener) {
            super(itemView);

            cliv_status = (ImageView)itemView.findViewById(R.id.cliv_status);
            cliv_img = (ImageView)itemView.findViewById(R.id.cliv_img);
            cltv_tanggal = (TextView)itemView.findViewById(R.id.cltv_tanggal);
            cltv_diajukan = (TextView)itemView.findViewById(R.id.cltv_diajukan);
            cltv_deskripsi = (TextView)itemView.findViewById(R.id.cltv_deskripsi);
            cltv_jumlah = (TextView)itemView.findViewById(R.id.cltv_jumlah);
            cltv_deskrp = (TextView)itemView.findViewById(R.id.cltv_deskrp);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;

        }

    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);

            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }


    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);

    }
}
