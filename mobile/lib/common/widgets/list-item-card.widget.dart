import 'package:flutter/material.dart';

class ListItemCard extends StatefulWidget {

  final Map<String, String> _values;
  final VoidCallback? onEdit;
  final VoidCallback? onDelete;
  final List<IconButton>? additionalActions;

  const ListItemCard(
    this._values, 
    {
      this.onEdit, 
      this.onDelete, 
      this.additionalActions,  
      Key? key
    }
  ) : super(key: key);

  @override
  State<StatefulWidget> createState() => _ListItemCardState();
}

class _ListItemCardState extends State<ListItemCard> {

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          for (var key in widget._values.keys)
          Container(
            padding: const EdgeInsetsDirectional.fromSTEB(10, 10, 10, 10),
            child: Row(
              children: [
                Text(
                  key + ':',
                  style: const TextStyle(
                    fontWeight: FontWeight.bold,
                    color: Colors.grey,
                    fontSize: 20,
                  ),
                ),
                Padding(
                  padding: const EdgeInsetsDirectional.fromSTEB(10, 0, 0, 0),
                  child: Text(
                    widget._values[key]!,
                    style: const TextStyle(
                      fontSize: 20,
                    ),
                  ),
                ),
              ],
            )
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: getActions(),
          ),
        ],
      ),
    );
  }

  getActions() {
    List<IconButton> actions = [];
    if (widget.onEdit != null) {
      actions.add(IconButton(
        onPressed: widget.onEdit, 
        icon: const Icon(Icons.edit),
        color:Colors.blue
      ));
    }
    if (widget.onDelete != null) {
      actions.add(IconButton(
        onPressed: widget.onDelete, 
        icon: const Icon(Icons.delete_forever),
        color: Colors.red
      ));
    }
    if (widget.additionalActions != null) {
      for (var additionalAction in widget.additionalActions!) {
        actions.add(additionalAction);
      }
    }
    return actions;
  }

}