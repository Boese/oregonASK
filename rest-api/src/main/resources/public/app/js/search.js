'use strict';

var providers = new Bloodhound({
  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
  queryTokenizer: Bloodhound.tokenizers.whitespace,
  limit: 10,
  prefetch: {
    url: 'http://localhost:8080/api/providers',
    filter: function(list) {
      return $.map(list, function(provider) { return { name: provider.name };
    });
  }
}
});

providers.initialize();

$('#prefetch .typeahead').typeahead(null, {
  name: 'providers',
  displayKey: 'name',
  // `ttAdapter` wraps the suggestion engine in an adapter that
  // is compatible with the typeahead jQuery plugin
  source: providers.ttAdapter()
});
