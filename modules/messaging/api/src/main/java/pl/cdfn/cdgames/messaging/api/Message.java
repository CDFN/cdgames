package pl.cdfn.cdgames.messaging.api;

public interface Message {

  byte[] serialize();

  void deserialize(byte[] bytes);
}
