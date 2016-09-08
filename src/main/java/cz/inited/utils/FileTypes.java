package cz.inited.utils;

/**
 * Created by ondre on 08.07.2016.
 */
public enum FileTypes {
	CONTROLLER,
	SERVICE,
	TEMPLATE;

	public static boolean contains(String name) {
		return dashedValueOf(name) != null;
	}

	public static FileTypes dashedValueOf(String name) {
		for (FileTypes type: FileTypes.values()) {
			if (("--" + type.name().toLowerCase()).equals(name)) {
				return type;
			}
		}
		return null;
	}
}
