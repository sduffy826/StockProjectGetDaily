package Extractor;

public class Day {
	long date;
	double open;
	double high;
	double low;
	double close;
	double adjclose;
	double volume;
	
	public Day(long d, double o, double h, double l, double c, double a, double v) {
		date = d;
		open = o;
		high = h;
		low = l;
		close = c;
		adjclose = a;
		volume = v;
	}
}
