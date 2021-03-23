package projectdefence.service;

import projectdefence.event.userDeleteEvent.UserDeleteEvent;
import projectdefence.event.userRegisterEvent.UserRegisterEvent;

import java.io.IOException;

public interface LoggerService {

     void logRegister(UserRegisterEvent applicationEvent) throws IOException;

     void logDelete(UserDeleteEvent userDeleteEvent) throws IOException;



}
