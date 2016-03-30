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

	@Override
	public JsonElement serialize(Task src, Type typeOfSrc,	JsonSerializationContext context) {
       JsonObject object = new JsonObject();
       
       object.addProperty("taskDescription", src.getTaskDescription());
       
       JsonElement startDate = context.serialize(src.getStartDate());
       object.add("startDate", startDate);
       long startMilliseconds = src.getStartDate().get(Calendar.MILLISECOND);
       object.addProperty("start-Date-milliseconds", startMilliseconds);
       
       JsonElement endDate = context.serialize(src.getEndDate());
       object.add("endDate", endDate);
       long endMilliseconds = src.getEndDate().get(Calendar.MILLISECOND);
       object.addProperty("end-Date-milliseconds", endMilliseconds);
       
       object.addProperty("location", src.getLocation());
       
       JsonElement remindDate = context.serialize(src.getRemindDate());
       object.add("remindDate", remindDate);
       long remindMilliseconds = src.getRemindDate().get(Calendar.MILLISECOND);
       object.addProperty("remind-Date-milliseconds", remindMilliseconds);
       
       object.addProperty("priority", src.getPriority());
       
       object.addProperty("taskIndex", src.getTaskIndex());
       
       return object;
//		return new JsonPrimitive(src.getTimeInMillis());
	}

	@Override
	public Task deserialize(JsonElement json, Type typeOfT,  JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        
		String taskDescript = object.get("taskDescription").getAsString();
		
		Calendar startDate = context.deserialize(object.get("startDate"), Calendar.class);
		startDate.set(Calendar.MILLISECOND, object.get("start-Date-milliseconds").getAsInt());
		
		Calendar endDate = context.deserialize(object.get("endDate"), Calendar.class);
		endDate.set(Calendar.MILLISECOND, object.get("end-Date-milliseconds").getAsInt());
		
		String location = object.get("location").getAsString();
		
		Calendar remindDate = context.deserialize(object.get("remindDate"), Calendar.class);
		remindDate.set(Calendar.MILLISECOND, object.get("remind-Date-milliseconds").getAsInt());
		
		String priority = object.get("priority").getAsString();
		
		int taskIndex = object.get("taskIndex").getAsInt();
		
		Task task = new Task(taskDescript, startDate, endDate, location, remindDate, priority, taskIndex);
		
        return task;
	}
}