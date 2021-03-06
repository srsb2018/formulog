#pragma once

#include <cstdlib>
#include <iostream>
#include <map>

namespace flg {

using namespace std;

enum class Symbol {
  min_term,
  boxed_bool,
  boxed_i32,
  boxed_i64,
  boxed_fp32,
  boxed_fp64,
  boxed_string,
/* INSERT 0 */
  max_term
};

ostream& operator<<(ostream& out, const Symbol& sym) {
	switch (sym) {
    case Symbol::min_term: return out << "min_term";
	  case Symbol::boxed_bool: return out << "boxed_bool";
	  case Symbol::boxed_i32: return out << "boxed_i32";
	  case Symbol::boxed_i64: return out << "boxed_i64";
	  case Symbol::boxed_fp32: return out << "boxed_fp32";
	  case Symbol::boxed_fp64: return out << "boxed_fp64";
	  case Symbol::boxed_string: return out << "boxed_string";
/* INSERT 1 */
    case Symbol::max_term: return out << "max_term";
	}
  __builtin_unreachable();
}

map<string, Symbol> symbol_table;

void initialize_symbols() {
/* INSERT 2 */
}

Symbol lookup_symbol(const string& name) {
  auto it = symbol_table.find(name);
  if (it != symbol_table.end()) {
    return it->second;
  }
  cerr << "Unrecognized symbol: " << name << endl;
  abort();
  __builtin_unreachable();
}

Symbol lookup_tuple_symbol(size_t arity) {
  switch (arity) {
/* INSERT 3 */
    default:
    cerr << "Unrecognized tuple arity: " << arity << endl;
    abort();
  }
  __builtin_unreachable();
}

size_t symbol_arity(const Symbol& sym) {
  switch (sym) {
/* INSERT 4 */
    default: abort();
  }
  __builtin_unreachable();
}

} // namespace flg
