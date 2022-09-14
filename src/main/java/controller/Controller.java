package controller;

import utils.RequestHeaderParser.RequestHeader;

public interface Controller {

  byte[] process(RequestHeader requestHeader);

  String getMappingUrl();
}
