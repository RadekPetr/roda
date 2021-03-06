package pt.gov.dgarq.roda.core.adapter.jndi;

import javax.naming.directory.Attributes;

import pt.gov.dgarq.roda.core.data.adapter.filter.OneOfManyFilterParameter;

/**
 * @author Rui Castro
 * @param <EA>
 *            the entity adapter for JNDI entities.
 */
public class JndiOneOfManyFilterParameter<EA extends JndiEntityAdapter> extends
		JndiFilterParameterAdapter<EA> {

	/**
	 * @param entityAdapter
	 * @param filterParameter
	 */
	public JndiOneOfManyFilterParameter(EA entityAdapter,
			OneOfManyFilterParameter filterParameter) {
		super(entityAdapter, filterParameter);
	}

	@Override
	public String getJndiFilter() {

		String jndiAttributeName = getEntityAdapter()
				.getJndiAttributeNameForAttribute(
						getFilterParameter().getName());

		String[] values = getOneOfManyFilterParameter().getValues();

		String jndiCondition = "";
		if (values != null) {

			if (values.length > 1) {
				jndiCondition += "(| ";
			}

			String firstJndiValue = getEntityAdapter().getJndiValueForAtribute(
					getFilterParameter().getName(), values[0]);
			jndiCondition += "(" + jndiAttributeName + "=" + firstJndiValue
					+ ")";

			for (int i = 1; i < values.length; i++) {

				String value = values[i];

				String jndiValue = getEntityAdapter().getJndiValueForAtribute(
						getFilterParameter().getName(), value);

				jndiCondition += "(" + jndiAttributeName + "=" + jndiValue
						+ ")";
			}

			if (values.length > 1) {
				jndiCondition += ")";
			}
		}

		return jndiCondition;
	}

	private OneOfManyFilterParameter getOneOfManyFilterParameter() {
		return (OneOfManyFilterParameter) getFilterParameter();
	}

	@Override
	public boolean canFilterValues() {
		return false;
	}

	@Override
	public boolean filterValue(Object attributes) {
		return false;
	}

}
