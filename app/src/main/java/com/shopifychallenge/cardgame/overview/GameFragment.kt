package com.shopifychallenge.cardgame.overview
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.shopifychallenge.cardgame.R
import com.shopifychallenge.cardgame.StartGameFragmentDirections
import com.shopifychallenge.cardgame.databinding.FragmentOverviewBinding
import com.wajahatkarim3.easyflipview.EasyFlipView


class GameFragment : Fragment() {

    private val viewModelFactory: ProductViewModelFactory by lazy {
        ProductViewModelFactory(GameFragmentArgs.fromBundle(arguments!!).numOfRows,
            GameFragmentArgs.fromBundle(arguments!!).numOfColumns,
            GameFragmentArgs.fromBundle(arguments!!).numOfPairs)
    }
    private val viewModel: ProductImageViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ProductImageViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel

        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener{pos, productImg ->
                viewModel.flipCard(productImg, pos)
        })

        viewModel.listToFlipBack.observe(this, Observer {
            if ( null != it ) {
                for(pos in it){
                    Handler().postDelayed({
                        (binding.photosGrid.getChildAt(pos) as? EasyFlipView)?.flipTheView()
                    }, 100)
                }

            }
        })

        viewModel.won.observe(this, Observer {
            if (it){
                MaterialDialog(context!!).show{
                    title(R.string.won_title)
                    message(R.string.won)
                    positiveButton(R.string.agree) { dialog ->
                        view.findNavController()
                            .navigate(GameFragmentDirections.actionOverviewFragmentToStartGameFragment())
                        }
                }
            }
        })

        binding.photosGrid.layoutManager = GridLayoutManager(activity, GameFragmentArgs.fromBundle(arguments!!).numOfColumns)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}
