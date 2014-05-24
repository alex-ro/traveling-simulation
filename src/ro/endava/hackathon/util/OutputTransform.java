package ro.endava.hackathon.util;

import ro.endava.hackathon.core.ProcessPerson;
import ro.endava.hackathon.core.Result;

import java.util.ArrayList;
import java.util.List;

public class OutputTransform {

	public static List<Result> getOutput(List<ProcessPerson> persons, Integer index) {
		List<Result> res = new ArrayList<Result>();
		for (ProcessPerson p : persons) {
			Result r = new Result();
			if (p.getProcessActivity() != null) {
				r.setActivity(p.getProcessActivity().getActivity());
			}
			r.setPerson(p.getPerson());
			r.setIndex(index);
		}
		return res;
	}
}
