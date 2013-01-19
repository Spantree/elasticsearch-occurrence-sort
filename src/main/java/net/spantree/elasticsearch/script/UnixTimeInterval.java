package net.spantree.elasticsearch.script;

public class UnixTimeInterval implements Comparable<UnixTimeInterval> {
	private Long start = null;
	private Long end = null;
	
	public void setStart(Long start) {
		this.start = start;
	}

	public void setEnd(Long end) {
		this.end = end;
	}
	
	public Long getStart() {
		return start;
	}

	public Long getEnd() {
		return end;
	}

	boolean contains(Long unixTime) {
		boolean contains = true;
		if(start != null && unixTime < start) {
			contains = false; 
		}
		if(end != null && unixTime > end) {
			contains = false;
		}
		return contains;
	}

	@Override
	public int compareTo(UnixTimeInterval o) {
		if(this.start == null && o.start != null) return -1;
		if(this.start != null && o.start == null) return 1;
		if(this.start < o.start) return -1;
		if(this.start > o.start) return 1;
		if(this.end == null && o.end != null) return 1;
		if(this.end != null && o.end == null) return -1;
		if(this.end < o.end) return -1;
		if(this.end > o.end) return 1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnixTimeInterval other = (UnixTimeInterval) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}
}
