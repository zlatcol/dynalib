var OptionAdder = {
    bind: function($adder, $select, url) {
        $adder.keyup(function(event) {
            if (13 == event.which) {
                OptionAdder._addOption(url, $(this).val(), $select);
                $adder.val('');
                return false;
            }
            return true;
        });
        $adder.keydown(function(event) {
            if (13 == event.which) {
                return false;
            }
            return true;
        });
    },
    _addOption: function(url, name, $elem) {
        if ($elem.val() != '' && !OptionAdder._hasOption($elem, name)) {
            $.post(url, { name: name },
                function(result) {
                    if (result.hasOwnProperty('id')) {
                        $elem.prepend('<option value="'+result.result+'">'+name+'</option>');
                    }
                    OptionAdder._selectOption($elem, name);
                }
            );
        } else {
            OptionAdder._selectOption($elem, name);
        }
    },
    _hasOption: function($elem, text) {
        var found = false;
        $elem.find('option').each(function() {
            if (text == $(this).text()) {
                found = true;
            }
        });
        return found;
    },
    _selectOption: function($elem, text) {
        $elem.find('option').each(function() {
            if (text == $(this).text()) {
                $(this).attr('selected', 1);
            }
        });
    }
}