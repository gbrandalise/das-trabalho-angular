import 'package:flutter/cupertino.dart';

class PageTitle extends StatelessWidget {

  final String _title;

  // ignore: use_key_in_widget_constructors
  const PageTitle(this._title);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsetsDirectional.fromSTEB(10, 10, 0, 0),
      child: Text(
        _title,
        style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
      )
    );
  }

}