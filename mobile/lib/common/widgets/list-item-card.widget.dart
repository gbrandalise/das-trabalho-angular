import 'package:flutter/material.dart';

class ListItemCard extends StatelessWidget {

  final Map<String, String> _values;

  const ListItemCard(this._values, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Column(
        children: [
          for (var key in _values.keys)
          Container(
            padding: const EdgeInsetsDirectional.fromSTEB(10, 10, 10, 10),
            child: Row(
              children: [
                  Text(
                    key + ':',
                    style: const TextStyle(
                      fontWeight: FontWeight.bold,
                      color: Colors.grey,
                      fontSize: 22,
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsetsDirectional.fromSTEB(10, 0, 0, 0),
                    child: Text(
                      _values[key]!,
                      style: const TextStyle(
                        fontSize: 20,
                      ),
                    ),
                  ),
              ],
            )
          ),
        ],
      ),
    );
  }

}