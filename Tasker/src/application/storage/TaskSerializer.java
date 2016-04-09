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

public class TaskSerializer implements JsonSerializer<Task>, JsonDeserializer<Task> {

	private static final String TASK_TYPE = "taskType";
	private static final String TASK_DESCRIPTION = "taskDescription";
	private static final String TASK_START_DATE = "startDate";
	private static final String TASK_START_DATE_MILLISECONDS = "start-Date-milliseconds";
	private static final String TASK_END_DATE = "endDate";
	private static final String TASK_END_DATE_MILLISECONDS = "end-Date-milliseconds";
	private static final String TASK_LOCATION = "location";
	private static final String TASK_REMIND_DATE = "remindDate";
	private static final String TASK_REMIND_DATE_MILLISECONDS = "remind-Date-milliseconds";
	private static final String TASK_PRIORITY = "priority";
	private static final String TASK_INDEX = "taskIndex";

	@Override
	public JsonElement serialize(Task src, Type typeOfSrc, JsonSerializationContext context) {

		JsonObject object = new JsonObject();
		String className = src.getClass().getSimpleName();
		object.addProperty(TASK_TYPE, className);
		object.addProperty(TASK_DESCRIPTION, src.getTaskDescription());

		if (src instanceof DeadlineTask) {
			JsonElement endDate = context.serialize(((DeadlineTask) src).getEndDate());
			object.add(TASK_END_DATE, endDate);
			long endMilliseconds = ((DeadlineTask) src).getEndDate().get(Calendar.MILLISECOND);
			object.addProperty(TASK_END_DATE_MILLISECONDS, endMilliseconds);
		} else if (src instanceof EventTask) {
			JsonElement startDate = context.serialize(((EventTask) src).getStartDate());
			object.add(TASK_START_DATE, startDate);
			long startMilliseconds = ((EventTask) src).getStartDate().get(Calendar.MILLISECOND);
			object.addProperty(TASK_START_DATE_MILLISECONDS, startMilliseconds);
			JsonElement endDate = context.serialize(((EventTask) src).getEndDate());
			object.add(TASK_END_DATE, endDate);
			long endMilliseconds = ((EventTask) src).getEndDate().get(Calendar.MILLISECOND);
			object.addProperty(TASK_END_DATE_MILLISECONDS, endMilliseconds);
		}

		object.addProperty(TASK_LOCATION, src.getLocation());
		JsonElement remindDate = context.serialize(src.getRemindDate());
		object.add(TASK_REMIND_DATE, remindDate);
		long remindMilliseconds = src.getRemindDate().get(Calendar.MILLISECOND);
		object.addProperty(TASK_REMIND_DATE_MILLISECONDS, remindMilliseconds);
		object.addProperty(TASK_PRIORITY, src.getPriority());
		object.addProperty(TASK_INDEX, src.getTaskIndex());

		return object;
	}

	@Override
	public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		JsonObject object = json.getAsJsonObject();
		String className = object.get(TASK_TYPE).getAsString();
		String taskDescript = object.get(TASK_DESCRIPTION).getAsString();
		String location = object.get(TASK_LOCATION).getAsString();
		String priority = object.get(TASK_PRIORITY).getAsString();
		int taskIndex = object.get(TASK_INDEX).getAsInt();
		Calendar remindDate = context.deserialize(object.get(TASK_REMIND_DATE), Calendar.class);
		remindDate.set(Calendar.MILLISECOND, object.get(TASK_REMIND_DATE_MILLISECONDS).getAsInt());

		if (className.equalsIgnoreCase(FloatingTask.class.getSimpleName())) {
			FloatingTask floatingTask = new FloatingTask(taskDescript, location, remindDate, priority, taskIndex);
			return floatingTask;
		} else if (className.equalsIgnoreCase(DeadlineTask.class.getSimpleName())) {
			Calendar endDate = context.deserialize(object.get(TASK_END_DATE), Calendar.class);
			endDate.set(Calendar.MILLISECOND, object.get(TASK_END_DATE_MILLISECONDS).getAsInt());
			DeadlineTask deadlineTask = new DeadlineTask(taskDescript, endDate, location, remindDate, priority,
					taskIndex);
			return deadlineTask;
		} else {
			Calendar startDate = context.deserialize(object.get(TASK_START_DATE), Calendar.class);
			startDate.set(Calendar.MILLISECOND, object.get(TASK_START_DATE_MILLISECONDS).getAsInt());
			Calendar endDate = context.deserialize(object.get(TASK_END_DATE), Calendar.class);
			endDate.set(Calendar.MILLISECOND, object.get(TASK_END_DATE_MILLISECONDS).getAsInt());
			EventTask eventTask = new EventTask(taskDescript, startDate, endDate, location, remindDate, priority,
					taskIndex);
			return eventTask;
		}
	}

}