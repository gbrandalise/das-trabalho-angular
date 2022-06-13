class HttpException implements Exception {

  int? statusCode;
  String message;

  HttpException(this.statusCode, this.message);
  
}