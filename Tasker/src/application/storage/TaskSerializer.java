//@@author A0125522R

package application.storage;

import java.lang.reflect.Type;
import java.util.Calendar;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TaskSerializer implements JsonSerializer<Task>, JsonDeserializer<Task>{

	private static final String TASKTYPE = "taskType";

	@Override
	public JsonElement serialize(Task src, Type typeOfSrc,	JsonSerializationContext context) {

		JsonObject object = new JsonObject();
	    String className = src.getClass().getSimpleName();
	    object.addProperty("taskType", className);
	    if (src instanceof FloatingTask) {
	        object.addProperty("taskDescription", src.getTaskDescription());
	        object.addProperty("location", src.getLocation());
	        JsonElement remindDate = context.serialize(src.getRemindDate());
	        object.add("remindDate", remindDate);
	        long remindMilliseconds = src.getRemindDate().get(Calendar.MILLISECOND);
	        object.addProperty("remind-Date-milliseconds", remindMilliseconds);
	        object.addProperty("priority", src.getPriority());	        
	        object.addProperty("taskIndex", src.getTaskIndex());
		    return object;
	    }
	    else if (src instanceof DeadlineTask) {
	        object.addProperty("taskDescription", src.getTaskDescription());
	        JsonElement endDate = context.serialize(((DeadlineTask) src).getEndDate());
	        object.add("endDate", endDate);
	        long endMilliseconds = ((DeadlineTask) src).getEndDate().get(Calendar.MILLISECOND);
	        object.addProperty("end-Date-milliseconds", endMilliseconds);
	        object.addProperty("location", src.getLocation());
	        JsonElement remindDate = context.serialize(src.getRemindDate());
	        object.add("remindDate", remindDate);
	        long remindMilliseconds = src.getRemindDate().get(Calendar.MILLISECOND);
	        object.addProperty("remind-Date-milliseconds", remindMilliseconds);
	        object.addProperty("priority", src.getPriority());	        
	        object.addProperty("taskIndex", src.getTaskIndex());
		    return object;
	    }
	    else if (src instanceof EventTask) {
	        object.addProperty("taskDescription", src.getTaskDescription());
	        JsonElement startDate = context.serialize(((EventTask) src).getStartDate());
	        object.add("startDate", startDate);
	        long startMilliseconds = ((EventTask) src).getStartDate().get(Calendar.MILLISECOND);
	        object.addProperty("start-Date-milliseconds", startMilliseconds);    
	        JsonElement endDate = context.serialize(((EventTask) src).getEndDate());
	        object.add("endDate", endDate);
	        long endMilliseconds = ((EventTask) src).getEndDate().get(Calendar.MILLISECOND);
	        object.addProperty("end-Date-milliseconds", endMilliseconds);
	        object.addProperty("location", src.getLocation());
	        JsonElement remindDate = context.serialize(src.getRemindDate());
	        object.add("remindDate", remindDate);
	        long remindMilliseconds = src.getRemindDate().get(Calendar.MILLISECOND);
	        object.addProperty("remind-Date-milliseconds", remindMilliseconds);
	        object.addProperty("priority", src.getPriority());	        
	        object.addProperty("taskIndex", src.getTaskIndex());
		    return object;
	    }
	    return object;
	}
	
	@Override
	public Task deserialize(JsonElement json, Type typeOfT,  JsonDeserializationContext context) throws JsonParseException {

		JsonObject object = json.getAsJsonObject();
		String className = object.get(TASKTYPE).getAsString();
		String taskDescript = object.get("taskDescription").getAsString();
		String location = object.get("location").getAsString();
		String priority = object.get("priority").getAsString();		
		int taskIndex = object.get("taskIndex").getAsInt();
		Calendar remindDate = context.deserialize(object.get("remindDate"), Calendar.class);
		remindDate.set(Calendar.MILLISECOND, object.get("remind-Date-milliseconds").getAsInt());
		
	    if (className.equalsIgnoreCase(FloatingTask.class.getSimpleName())) {
	    	FloatingTask floatingTask = new FloatingTask(taskDescript, location, remindDate, priority, taskIndex);			
	        return floatingTask;	
	    }
	    else if (className.equalsIgnoreCase(DeadlineTask.class.getSimpleName())) {
			Calendar endDate = context.deserialize(object.get("endDate"), Calendar.class);
			endDate.set(Calendar.MILLISECOND, object.get("end-Date-milliseconds").getAsInt());
			DeadlineTask deadlineTask = new DeadlineTask(taskDescript, endDate, location, remindDate, priority, taskIndex);			
	        return deadlineTask;
	    }
	    else {
			Calendar startDate = context.deserialize(object.get("startDate"), Calendar.class);
			startDate.set(Calendar.MILLISECOND, object.get("start-Date-milliseconds").getAsInt());		
			Calendar endDate = context.deserialize(object.get("endDate"), Calendar.class);
			endDate.set(Calendar.MILLISECOND, object.get("end-Date-milliseconds").getAsInt());
			EventTask eventTask = new EventTask(taskDescript, startDate, endDate, location, remindDate, priority, taskIndex);			
	        return eventTask;
	    }		
	}

}