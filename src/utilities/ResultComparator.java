package utilities;

import java.util.Comparator;
import structures.*;

public class ResultComparator implements Comparator<ComparisonResult> {

	@Override
	public int compare(ComparisonResult result0, ComparisonResult result1) {
		return -Double.compare(result0.ratioSharedVSExtracted(), result1.ratioSharedVSExtracted());
	}

}
