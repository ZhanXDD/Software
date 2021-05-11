package domain;

import java.util.List;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Team {

	@Id
	String name;
	@OneToMany
	List<Event> events;

	public Team(String team) {
		this.name = team;
		events = new Vector<Event>();
	}

	public String getName() {
		return this.name;
	}

	public List<Event> getEvents(){
		return events;
	}

	public Event addEvent(Event ev) {
		events.add(ev);
		return ev;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object ob) {
		if(ob == null) {
			return false;
		}else {
			if(this == ob) {
				return true;
			}else {
				if(this.getClass() != ob.getClass()) {
					return false;
				}else {
					Team t = (Team) ob;
					if(this.getName().equals(t.getName())) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
