package com.jobinlawrance.perpuleassignment.ui.audiodetail.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.jobinlawrance.perpuleassignment.R
import kotlinx.android.synthetic.main.fragment_audio_detail.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AudioDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AudioDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AudioDetailFragment : Fragment() {

    private var desc: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var isViewCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            desc = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        audio_name.text = desc
        next_button.setOnClickListener {
            listener?.onClickNext()
        }
        isViewCreated = true
    }

    fun showLoadingState(isLoading: Boolean) {
        if (loading_text != null)
            loading_text.isVisible = isLoading
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onClickNext()
    }

    companion object {
        @JvmStatic
        fun newInstance(desc: String) =
            AudioDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, desc)
                }
            }
    }
}
