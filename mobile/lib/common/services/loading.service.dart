import 'package:flutter/material.dart';

class LoadingService {

  static void show(BuildContext context) {
    Future.delayed(Duration.zero, () => {
      showDialog(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return const Dialog(
            backgroundColor: Colors.transparent,
            insetPadding: EdgeInsets.all(170),
            child: SizedBox(
              child: CircularProgressIndicator(),
              height: 60,
              width: 60,
            ),
          );
        },
      )
    });
  }

  static void hide(BuildContext context) {
    Navigator.pop(context);
  }

}