package JSONReader;

import Request.LoginRequest;
import Request.RegisterRequest;
import Result.*;
import com.google.gson.Gson;

public class Serializer {

    public static String serialize(ClearResult returnType) {
        return (new Gson()).toJson(returnType);
    }

    public static String serialize(EventByIdResult returnType) {
        return (new Gson()).toJson(returnType);
    }

    public static String serialize(EventsResult returnType) {
        return (new Gson()).toJson(returnType);
    }

    public static String serialize(FillResult returnType) {
        return (new Gson()).toJson(returnType);
    }

    public static String serialize(LoadResult returnType) {
        return (new Gson()).toJson(returnType);
    }

    public static String serialize(LoginRequest returnType) {
        return (new Gson()).toJson(returnType);
    }

    public static String serialize(LoginResult returnType) {
        return (new Gson()).toJson(returnType);
    }

    public static String serialize(PersonByIdResult returnType) {
        return (new Gson()).toJson(returnType);
    }

    public static String serialize(PersonsResult returnType) {
        return (new Gson()).toJson(returnType);
    }

    public static String serialize(RegisterRequest returnType) {
        return (new Gson()).toJson(returnType);
    }

    public static String serialize(RegisterResult returnType) {
        return (new Gson()).toJson(returnType);
    }
}
