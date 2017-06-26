package kr.yookn.usedbooksearcher.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import kr.yookn.usedbooksearcher.BookInfo;
import kr.yookn.usedbooksearcher.IsbnInfo;
import kr.yookn.usedbooksearcher.R;
import kr.yookn.usedbooksearcher.helper.CircleTransform;
import kr.yookn.usedbooksearcher.helper.FlipAnimator;

/**
 * Created by Ravi Tamada on 21/02/17.
 * www.androidhive.info
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private Context mContext;
    private List<IsbnInfo> isbnInfos;
    private MessageAdapterListener listener;
    private SparseBooleanArray selectedItems;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    private static int currentSelectedIndex = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView from, subject, message, iconText, timestamp;
        public ImageView iconImp, imgProfile;
        public LinearLayout messageContainer;
        public RelativeLayout iconContainer, iconBack, iconFront;
        public Button yes24Price, aladinPrice, interparkPrice;
        public Button topPriceClick, middlePriceClick, lowPriceClick;

        public MyViewHolder(View view) {
            super(view);
            from = (TextView) view.findViewById(R.id.from);
            subject = (TextView) view.findViewById(R.id.txt_primary);
            message = (TextView) view.findViewById(R.id.txt_secondary);
            iconText = (TextView) view.findViewById(R.id.icon_text);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            iconBack = (RelativeLayout) view.findViewById(R.id.icon_back);
            iconFront = (RelativeLayout) view.findViewById(R.id.icon_front);
            iconImp = (ImageView) view.findViewById(R.id.icon_star);
            imgProfile = (ImageView) view.findViewById(R.id.icon_profile);
            messageContainer = (LinearLayout) view.findViewById(R.id.message_container);
            iconContainer = (RelativeLayout) view.findViewById(R.id.icon_container);

            yes24Price  = (Button) view.findViewById(R.id.yes24Price);
            aladinPrice  = (Button) view.findViewById(R.id.aladinPrice);
            interparkPrice  = (Button) view.findViewById(R.id.interparkPrice);


            topPriceClick  = (Button) view.findViewById(R.id.topPriceClick);
            middlePriceClick  = (Button) view.findViewById(R.id.middlePriceClick);
            lowPriceClick  = (Button) view.findViewById(R.id.lowPriceClick);


            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }


    public MessagesAdapter(Context mContext, List<IsbnInfo> isbnInfos, MessageAdapterListener listener) {
        this.mContext = mContext;
        this.isbnInfos = isbnInfos;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final IsbnInfo isbnInfo = isbnInfos.get(position);

        for (int i=0; i < isbnInfos.size(); i++) {

            IsbnInfo row = isbnInfos.get(i);
            Log.d("entrv","aa1 >> " + row.getIsbn());
            IsbnInfo.NaverBookItem naverBookItems = row.getNaverBookItems().get(0);
            Log.d("entrv","aa1 >> " + naverBookItems.getPrice());
            Log.d("entrv","aa1 >> " + naverBookItems.getImage());
            Log.d("entrv","aa1 >> " + naverBookItems.getTitle());
            Log.d("entrv","aa1 >> " + naverBookItems.getDescription());


            for (int j=0; j < isbnInfo.getBookinfo().size(); j++) {
                BookInfo b = isbnInfo.getBookinfo().get(j);
                if (isbnInfo.getShowPrice() == 1) {
                    //최상
                } else if (isbnInfo.getShowPrice() == 1) {
                    //중상
                } else if (isbnInfo.getShowPrice() == 1) {
                    //상
                }
                Log.d("entrv","bb1 >> " +b.getSitename()
                        + " >> " + b.getOrginalMoney()
                        + " >> " + b.getTopMoney()
                        + " >> " + b.getMiddleMoney()
                        + " >> " + b.getLowMoney()
                );
            }

        }

        //holder.topPriceClick.setBackgroundColor(Color.parseColor("#EF473A"));
        String yes24Price = "", aladinPrice = "", interparkPrice = "";
        for (int j=0; j < isbnInfo.getBookinfo().size(); j++) {
            BookInfo b = isbnInfo.getBookinfo().get(j);
            if (isbnInfo.getShowPrice() == 1) {
                if (b.getSitename().equals("yes24")) {
                    yes24Price = b.getTopMoney();
                }
                if (b.getSitename().equals("aladin")) {
                    aladinPrice = b.getTopMoney();
                }
                if (b.getSitename().equals("interpark")) {
                    interparkPrice = b.getTopMoney();
                }
                //최상
            } else if (isbnInfo.getShowPrice() == 2) {
                if (b.getSitename().equals("yes24")) {
                    yes24Price = b.getMiddleMoney();
                }
                if (b.getSitename().equals("aladin")) {
                    aladinPrice = b.getMiddleMoney();
                }
                if (b.getSitename().equals("interpark")) {
                    interparkPrice = b.getMiddleMoney();
                }
                //중상
            } else if (isbnInfo.getShowPrice() == 3) {
                //상
                if (b.getSitename().equals("yes24")) {
                    yes24Price = b.getLowMoney();
                }
                if (b.getSitename().equals("aladin")) {
                    aladinPrice = b.getLowMoney();
                }
                if (b.getSitename().equals("interpark")) {
                    interparkPrice = b.getLowMoney();
                }
            }
            Log.d("entrv","bb1 >> " +b.getSitename()
                    + " >> " + b.getOrginalMoney()
                    + " >> " + b.getTopMoney()
                    + " >> " + b.getMiddleMoney()
                    + " >> " + b.getLowMoney()
            );
        }


        holder.topPriceClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isbnInfo.setShowPrice(1);
                Log.d("entrv","topPriceClicked");
                holder.topPriceClick.setBackgroundColor(Color.parseColor("#FFC900"));
                holder.middlePriceClick.setBackgroundColor(Color.parseColor("#FFC900"));
                holder.lowPriceClick.setBackgroundColor(Color.parseColor("#FFC900"));

                holder.topPriceClick.setBackgroundColor(Color.parseColor("#EF473A"));
                notifyDataSetChanged();
            }
        });
        holder.middlePriceClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isbnInfo.setShowPrice(2);
                Log.d("entrv","middlePriceClick");
                notifyDataSetChanged();
                holder.topPriceClick.setBackgroundColor(Color.parseColor("#FFC900"));
                holder.middlePriceClick.setBackgroundColor(Color.parseColor("#FFC900"));
                holder.lowPriceClick.setBackgroundColor(Color.parseColor("#FFC900"));
                holder.middlePriceClick.setBackgroundColor(Color.parseColor("#EF473A"));

            }
        });
        holder.lowPriceClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isbnInfo.setShowPrice(3);
                Log.d("entrv","lowPriceClick");
                notifyDataSetChanged();
                holder.topPriceClick.setBackgroundColor(Color.parseColor("#FFC900"));
                holder.middlePriceClick.setBackgroundColor(Color.parseColor("#FFC900"));
                holder.lowPriceClick.setBackgroundColor(Color.parseColor("#FFC900"));
                holder.lowPriceClick.setBackgroundColor(Color.parseColor("#EF473A"));

            }
        });

        holder.yes24Price.setText(yes24Price);
        holder.aladinPrice.setText(aladinPrice);
        holder.interparkPrice.setText(interparkPrice);
        // displaying text view data
        holder.from.setText(isbnInfo.getIsbn());
        holder.subject.setText(isbnInfo.getNaverBookItems().get(0).getTitle());
        holder.message.setText(isbnInfo.getNaverBookItems().get(0).getDescription());
        holder.timestamp.setText(isbnInfo.getNaverBookItems().get(0).getAuthor());

        // displaying the first letter of From in icon text
        holder.iconText.setText(isbnInfo.getNaverBookItems().get(0).getPubdate().substring(0, 1));

        // change the row state to activated
        //holder.itemView.setActivated(selectedItems.get(position, false));

        // change the font style depending on message read status
        applyReadStatus(holder, isbnInfo);

        // handle message star
        applyImportant(holder, isbnInfo);

        // handle icon animation
        applyIconAnimation(holder, position);

        // display profile image
        applyProfilePicture(holder, isbnInfo);

        // apply click events
        applyClickEvents(holder, position);
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconClicked(position);
            }
        });

        holder.iconImp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconImportantClicked(position);
            }
        });

        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClicked(position);
            }
        });

        holder.messageContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onRowLongClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
    }

    private void applyProfilePicture(MyViewHolder holder, IsbnInfo isbnInfo) {
        if (!TextUtils.isEmpty(isbnInfo.getNaverBookItems().get(0).getImage())) {
            Glide.with(mContext).load(isbnInfo.getNaverBookItems().get(0).getImage())
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imgProfile);
            holder.imgProfile.setColorFilter(null);
            holder.iconText.setVisibility(View.GONE);
        } else {
            holder.imgProfile.setImageResource(R.drawable.bg_circle);
            //holder.imgProfile.setColorFilter(isbnInfo.getColor());
            holder.iconText.setVisibility(View.VISIBLE);
        }
    }

    private void applyIconAnimation(MyViewHolder holder, int position) {
        if (selectedItems.get(position, false)) {
            holder.iconFront.setVisibility(View.GONE);
            resetIconYAxis(holder.iconBack);
            holder.iconBack.setVisibility(View.VISIBLE);
            holder.iconBack.setAlpha(1);
            if (currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, true);
                resetCurrentIndex();
            }
        } else {
            holder.iconBack.setVisibility(View.GONE);
            resetIconYAxis(holder.iconFront);
            holder.iconFront.setVisibility(View.VISIBLE);
            holder.iconFront.setAlpha(1);
            if ((reverseAllAnimations && animationItemsIndex.get(position, false)) || currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, false);
                resetCurrentIndex();
            }
        }
    }


    // As the views will be reused, sometimes the icon appears as
    // flipped because older view is reused. Reset the Y-axis to 0
    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(isbnInfos.get(position).getIsbn());
    }

    private void applyImportant(MyViewHolder holder, IsbnInfo isbnInfo) {
        //if (isbnInfo.isImportant()) {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_black_24dp));
            holder.iconImp.setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_selected));
        //} else {
        //    holder.iconImp.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_border_black_24dp));
         //   holder.iconImp.setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
        //}
    }

    private void applyReadStatus(MyViewHolder holder, IsbnInfo isbnInfo) {
        //if (isbnInfo.isRead()) {
            holder.from.setTypeface(null, Typeface.NORMAL);
            holder.subject.setTypeface(null, Typeface.NORMAL);
            holder.from.setTextColor(ContextCompat.getColor(mContext, R.color.subject));
            holder.subject.setTextColor(ContextCompat.getColor(mContext, R.color.message));
       // } else {
        //    holder.from.setTypeface(null, Typeface.BOLD);
       //     holder.subject.setTypeface(null, Typeface.BOLD);
        //    holder.from.setTextColor(ContextCompat.getColor(mContext, R.color.from));
        //    holder.subject.setTextColor(ContextCompat.getColor(mContext, R.color.subject));
       // }
    }

    @Override
    public int getItemCount() {
        return isbnInfos.size();
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
            animationItemsIndex.delete(pos);
        } else {
            selectedItems.put(pos, true);
            animationItemsIndex.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        reverseAllAnimations = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        isbnInfos.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public interface MessageAdapterListener {
        void onIconClicked(int position);

        void onIconImportantClicked(int position);

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);
    }
}