package com.example.bookvibez;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class LeaderboardTabUsers extends Fragment {

    private List<User> userList = random_users();
    private RecyclerView recyclerView;
    public static UsersLeaderAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leaderboard_tab_users, container, false);

        /* sort */
        Comparator<User> cmp = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return Integer.compare(u2.getVibePoints(), u1.getVibePoints());
            }
        };
        Collections.sort(userList, cmp);

        handlingRecycleViewer(view);
        return view;
    }


    /**
     * the function handles the RecycleView object in content_scrolling_list.
     * it defines the viewer and set a manager and an adapter to it.
     * @param view - current view (content_scrolling_list)
     */
    private void handlingRecycleViewer(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_users);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new UsersLeaderAdapter(userList, new UsersLeaderAdapter.OnItemClickListener() {
            @Override public void onItemClick(User user) {
                //loadProfilePageFragment();
                Toast.makeText(getContext(), user.getName() + " was chosen", Toast.LENGTH_SHORT).show();

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * this function replaces the layout to a book page layout in case some book was clicked in the list
     */
    private void loadProfilePageFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("ListView");  // enables to press "return" and go back to the list view
        transaction.replace(R.id.main_fragment_container, new ProfileFragment());
        transaction.commit();
    }


    public static List<User> random_users() {
        List<User> l = new ArrayList<>();
        User b3 = new User("Tom Saltsberg",78,  R.mipmap.man_icon);
        User b1 = new User("Bob Marley", 60, R.mipmap.man_icon);
        User b11 = new User("Lior Saadon", 95, R.mipmap.man_icon);
        User b22 = new User("Madonna", 56, R.mipmap.man_icon);
        User b2 = new User("Neta Zohar", 42, R.mipmap.man_icon);
        User b33 = new User("Yair Netanyahu", 4, R.mipmap.man_icon);
        User b44 = new User("WonderWoman", 13, R.mipmap.man_icon);
        User b4 = new User("Beyonce", 78, R.mipmap.man_icon);
        l.add(b1); l.add(b2); l.add(b3); l.add(b4);
        l.add(b11); l.add(b22); l.add(b33); l.add(b44);
        return l;
    }


}


class UsersLeaderAdapter extends RecyclerView.Adapter<UsersLeaderAdapter.UserViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    private final List<User> users;
    private final OnItemClickListener mListener;


    public UsersLeaderAdapter(List<User> list, OnItemClickListener listener) {
        this.users = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_leaderboard, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        holder.bind(users.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView title, info;
        private ImageView img;
        private FrameLayout frame;
        private View mView;

        public UserViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.user_name1);
            info = (TextView) view.findViewById(R.id.user_info);
            img = (ImageView) view.findViewById(R.id.user_image);
            frame = (FrameLayout) view.findViewById(R.id.frameLayout_points);
        }

        public void bind(final User user, final OnItemClickListener listener) {
            title.setText(user.getName());
            info.setText(user.getVibePointsString());

            int newWidth = 10*user.getVibePoints(); // Leaderboard.screenWidth);
            if (newWidth <= 0)
                newWidth = 100;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(frame.getLayoutParams());
            params.width = newWidth;
            frame.setLayoutParams(params);

            img.setImageResource(user.getUserImg());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    listener.onItemClick(user);
                }
            });
        }

    }
}





