package com.niit.backend.dao;

import java.util.List;

import com.niit.backend.model.Event;

public interface EventDao 
{
	public void postEvent(Event event);

	public Event getEventById(int eventid);

	public List<Event> getAllEvents();

	public Event updateEvent(int eventid, Event event);

	public void deleteEvent(int eventid);

}
